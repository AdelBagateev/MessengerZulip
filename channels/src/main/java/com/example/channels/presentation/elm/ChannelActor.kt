package com.example.channels.presentation.elm

import com.example.channels.di.deps.ChannelRouter
import com.example.channels.domain.model.StreamModel
import com.example.channels.domain.stream.usecase.CreateNewStreamUseCase
import com.example.channels.domain.stream.usecase.LoadStreamsUseCase
import com.example.channels.domain.stream.usecase.SubscribeOnStreamUseCase
import com.example.channels.domain.topic.usecase.LoadTopicsByStreamUseCase
import com.example.channels.domain.topic.usecase.SubscribeOnTopicsByStreamUseCase
import com.example.channels.presentation.mapper.StreamDomainToUiMapper
import com.example.channels.presentation.mapper.TopicDomainToUiMapper
import com.example.channels.presentation.ui.delegates.stream.StreamDelegateItem
import com.example.channels.presentation.ui.delegates.topic.TopicDelegateItem
import com.example.common.adapter.interfaces.DelegateItem
import com.example.common.catchCancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import timber.log.Timber
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class ChannelActor @Inject constructor(
    private val subscribeOnStreamUseCase: SubscribeOnStreamUseCase,
    private val loadStreamsUseCase: LoadStreamsUseCase,
    private val subscribeOnTopicsByStreamUseCase: SubscribeOnTopicsByStreamUseCase,
    private val loadTopicsByStreamUseCase: LoadTopicsByStreamUseCase,
    private val createNewStreamUseCase: CreateNewStreamUseCase,
    private val router: ChannelRouter,
    private val streamDomainToUiMapper: StreamDomainToUiMapper,
    private val topicDomainToUiMapper: TopicDomainToUiMapper,
) : Actor<ChannelCommand, ChannelEvent> {
    private var currentSubscribedStreamList: List<DelegateItem> = emptyList()
    private var currentAllStreamList: List<DelegateItem> = emptyList()
    override fun execute(command: ChannelCommand): Flow<ChannelEvent> {
        return when (command) {
            is ChannelCommand.SubscribeOnStreams -> subscribeOnStreams(command.isSubscribedFragment)
            is ChannelCommand.GetActualStreamsData -> getActualStreamsData(command.isSubscribedFragment)
            is ChannelCommand.CreateNewStream -> createNewStream(
                command.streamName,
                command.description
            )
            is ChannelCommand.SubscribeOnTopics -> showTopics(
                command.streamModel,
                command.isSubscribedFragment
            )
            is ChannelCommand.GetActualTopicsData -> getActualTopicsData(
                command.streamModel,
            )
            is ChannelCommand.TopicPressed -> navigateToDialog(
                command.streamName,
                command.topicName
            )
            is ChannelCommand.StreamPressed -> navigateToDialog(command.streamName, null)
            is ChannelCommand.SetupQueryState -> filterStreamList(
                command.searchQueryState,
                command.isSubscribedFragment
            )
            is ChannelCommand.Init -> updateLocalLists(
                command.subscribedStreamList,
                command.allStreamList
            )
        }
    }

    private fun createNewStream(streamName: String, description: String): Flow<ChannelEvent> {
        return flow<ChannelEvent> {
            createNewStreamUseCase(streamName, description)
        }.mapEvents {
            ChannelEvent.Internal.ErrorCreateNewStream
        }
    }

    private fun getActualTopicsData(
        streamModel: StreamModel,
    ): Flow<ChannelEvent> {
        return flow {
            if (!streamModel.isChosen) {
                emit(loadTopicsByStreamUseCase(streamModel))
            }
        }
            .mapEvents {
                ChannelEvent.Internal.ErrorTopicsOfStreamLoading
            }

    }

    private fun getActualStreamsData(isSubscribedFragment: Boolean): Flow<ChannelEvent> {
        return flow {
            emit(loadStreamsUseCase(isSubscribedFragment))
        }.flowOn(Dispatchers.Default)
            .mapEvents {
                ChannelEvent.Internal.ErrorStreamLoading(it, isSubscribedFragment)
            }
    }

    private fun subscribeOnStreams(isSubscribedFragment: Boolean): Flow<ChannelEvent> {
        return subscribeOnStreamUseCase(isSubscribedFragment)
            .distinctUntilChanged()
            .flatMapLatest {
                val list = if (isSubscribedFragment) {
                    currentSubscribedStreamList =
                        streamDomainToUiMapper.toDelegateItem(it)
                    currentSubscribedStreamList
                } else {
                    currentAllStreamList = streamDomainToUiMapper.toDelegateItem(it)
                    currentAllStreamList
                }
                flowOf(list)
            }.flowOn(Dispatchers.Default)
            .mapEvents(
                { list ->
                    if (isSubscribedFragment) {
                        ChannelEvent.Internal.SubscribedStreamListLoaded(list)
                    } else {
                        ChannelEvent.Internal.AllStreamListLoaded(list)
                    }
                },
                { error ->
                    catchCancellationException(error)
                    Timber.e(error.message)
                    ChannelEvent.Internal.ErrorStreamLoading(error, isSubscribedFragment)
                }
            )
    }

    private fun filterStreamList(
        searchQueryState: MutableSharedFlow<String>,
        isSubscribedFragment: Boolean
    ): Flow<ChannelEvent> {
        return searchQueryState
            .distinctUntilChanged()
            .debounce(300L)
            .flatMapLatest { flow { emit(search(it, isSubscribedFragment)) } }
            .flatMapConcat {
                flow {
                    if (isSubscribedFragment) {
                        emit(ChannelEvent.Internal.SubscribedStreamListFiltered(it))
                    } else {
                        emit(ChannelEvent.Internal.AllStreamListFiltered(it))
                    }
                }
            }
            .flowOn(Dispatchers.Default)
    }

    private fun search(query: String, isSubscribedFragment: Boolean): List<DelegateItem> {
        val currentList =
            if (isSubscribedFragment) currentSubscribedStreamList.toMutableList() else currentAllStreamList.toMutableList()
        if (query.isEmpty()) return currentList

        val result = currentList.filterIsInstance<StreamDelegateItem>().filter { stream ->
            val name = stream.value.name
            name.lowercase().contains(query.lowercase())
        }
        return result
    }

    private fun navigateToDialog(streamName: String, topicName: String?): Flow<ChannelEvent> {
        router.navigateToMessenger(streamName, topicName)
        return flow { emit(ChannelEvent.Internal.SuccessNavigate) }
    }

    private fun updateLocalLists(
        subscribedStreamList: List<DelegateItem>?,
        allStreamList: List<DelegateItem>?
    ): Flow<ChannelEvent> {
        subscribedStreamList?.let { currentSubscribedStreamList = it }
        allStreamList?.let { currentAllStreamList = it }
        return emptyFlow()
    }

    private fun showTopics(
        streamModel: StreamModel,
        isSubscribedFragment: Boolean
    ): Flow<ChannelEvent> {
        return flow {
            val newList = calculateNewList(isSubscribedFragment)
            val indexOfStream = calculateIndexOfStream(newList, streamModel)
            updateChosenStream(newList, streamModel, indexOfStream)
            updateCurrentList(newList, isSubscribedFragment)

            if (streamModel.isChosen) {
                removeTopics(newList, streamModel)
                emit(newList)
            } else {
                subscribeOnTopics(newList, streamModel, indexOfStream)
                    .collect { this@flow.emit(it) }
            }
        }.flowOn(Dispatchers.Default)
            .mapEvents({
                if (isSubscribedFragment) {
                    ChannelEvent.Internal.SubscribedStreamListLoaded(it)
                } else {
                    ChannelEvent.Internal.AllStreamListLoaded(it)
                }
            }, {
                ChannelEvent.Internal.ErrorTopicsOfStreamLoading
            }
            )
    }

    private fun subscribeOnTopics(
        newList: MutableList<DelegateItem>,
        streamModel: StreamModel,
        indexOfStream: Int
    ): Flow<List<DelegateItem>> {
        return subscribeOnTopicsByStreamUseCase(streamModel)
            .distinctUntilChanged()
            .flatMapLatest {
                flowOf(topicDomainToUiMapper.toDelegateItem(it))
            }.flowOn(Dispatchers.Default)
            .flatMapConcat { topics ->
                newList.addTopics(topics, indexOfStream, streamModel)
                flowOf(newList)
            }
    }

    private fun calculateIndexOfStream(
        newList: MutableList<DelegateItem>,
        streamModel: StreamModel
    ): Int {
        return newList.indexOfFirst { it is StreamDelegateItem && it.value.name == streamModel.name }
    }

    private fun calculateNewList(isSubscribedFragment: Boolean): MutableList<DelegateItem> {
        return if (isSubscribedFragment) {
            currentSubscribedStreamList.toMutableList()
        } else {
            currentAllStreamList.toMutableList()
        }
    }

    private fun updateCurrentList(
        newList: MutableList<DelegateItem>,
        isSubscribedFragment: Boolean
    ) {
        if (isSubscribedFragment) {
            currentSubscribedStreamList = newList
        } else {
            currentAllStreamList = newList
        }
    }

    private fun updateChosenStream(
        newList: MutableList<DelegateItem>,
        streamModel: StreamModel,
        indexOfStream: Int
    ) {
        newList.removeAt(indexOfStream)
        newList.add(
            indexOfStream, StreamDelegateItem(streamModel.copy(isChosen = !streamModel.isChosen))
        )
    }

    private fun MutableList<DelegateItem>.addTopics(
        topics: List<TopicDelegateItem>,
        indexOfStream: Int,
        streamModel: StreamModel
    ) {
        //удаляем устаревшие данные если такие есть
        removeTopics(this, streamModel)
        //добавляем актуальные
        this.addAll(indexOfStream + 1, topics)
    }

    private fun removeTopics(list: MutableList<DelegateItem>, streamModel: StreamModel) {
        list.removeAll {
            it is TopicDelegateItem && it.value.stream.id == streamModel.id
        }
    }
}

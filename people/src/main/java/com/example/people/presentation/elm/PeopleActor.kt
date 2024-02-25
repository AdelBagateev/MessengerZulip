package com.example.people.presentation.elm

import com.example.common.adapter.interfaces.DelegateItem
import com.example.common.catchCancellationException
import com.example.people.di.deps.PeopleRouter
import com.example.people.domain.model.PeopleModel
import com.example.people.domain.usecase.LoadPeopleUseCase
import com.example.people.domain.usecase.SubscribeOnPeopleUseCase
import com.example.people.presentation.mapper.PeopleDomainToUiMapper
import com.example.people.presentation.ui.delegate.PeopleDelegateItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import timber.log.Timber
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class PeopleActor @Inject constructor(
    private val loadPeopleUseCase: LoadPeopleUseCase,
    private val subscribeOnPeopleUseCase: SubscribeOnPeopleUseCase,
    private val router: PeopleRouter,
    private val peopleDomainToUiMapper: PeopleDomainToUiMapper
) : Actor<PeopleCommand, PeopleEvent> {
    private var currentList: List<DelegateItem> = emptyList()
    override fun execute(command: PeopleCommand): Flow<PeopleEvent> {
        return when (command) {
            is PeopleCommand.SubscribeOnPeople -> subscribeOnPeople()
            is PeopleCommand.Init -> updateLocalList(command.list)
            PeopleCommand.GetActualPeopleData -> getActualData()
            is PeopleCommand.OnPeopleClicked -> navigate(command.people)
            is PeopleCommand.SetupQueryState -> filterPeopleList(command.searchQueryState)
        }
    }

    private fun getActualData(): Flow<PeopleEvent> {
        return flow {
            emit(loadPeopleUseCase())
        }.mapEvents { error ->
            catchCancellationException(error)
            Timber.e(error.message)
            PeopleEvent.Internal.ErrorLoading(error)
        }
    }

    private fun filterPeopleList(searchQueryState: MutableSharedFlow<String>): Flow<PeopleEvent> {
        return searchQueryState
            .distinctUntilChanged()
            .debounce(300L)
            .flatMapLatest { query -> flow { emit(search(query)) } }
            .mapEvents({ PeopleEvent.Internal.PeopleFiltered(it) })
            .flowOn(Dispatchers.Default)
    }

    private fun subscribeOnPeople(): Flow<PeopleEvent> {
        return subscribeOnPeopleUseCase()
            .distinctUntilChanged()
            .flatMapLatest { newList ->
                currentList = peopleDomainToUiMapper.toDelegateItem(newList)
                flowOf(currentList)
            }
            .mapEvents(
                { newList ->
                    PeopleEvent.Internal.PeopleLoaded(newList)
                },
                { error ->
                    catchCancellationException(error)
                    Timber.e(error.message)
                    PeopleEvent.Internal.ErrorLoading(error)
                }
            ).flowOn(Dispatchers.Default)

    }

    private fun search(query: String): List<DelegateItem> {
        if (query.isEmpty()) return currentList
        val result = currentList
            .filterIsInstance<PeopleDelegateItem>()
            .filter { peopleDelegate ->
                val name = peopleDelegate.value.fullName
                name.lowercase().contains(query.lowercase())
            }
        return result
    }

    private fun updateLocalList(list: List<DelegateItem>?): Flow<PeopleEvent> {
        list?.let { currentList = it }
        return emptyFlow()
    }

    private fun navigate(people: PeopleModel): Flow<PeopleEvent> {
        return if (people.isBot) {
            flow {
                emit(PeopleEvent.Internal.ErrorIsBotNavigate)
            }
                .flowOn(Dispatchers.Default)
        } else {
            router.navigateToProfile(people.id, true)
            flow {
                emit(PeopleEvent.Internal.SuccessNavigate)
            }
                .flowOn(Dispatchers.Default)
        }
    }
}

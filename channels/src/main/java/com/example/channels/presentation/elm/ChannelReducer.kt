package com.example.channels.presentation.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject

class ChannelReducer @Inject constructor() :
    DslReducer<ChannelEvent, ChannelState, ChannelEffect, ChannelCommand>() {
    override fun Result.reduce(event: ChannelEvent): Any {
        return when (event) {
            //UI
            ChannelEvent.Ui.InitFromStream -> {}
            ChannelEvent.Ui.InitFromChannel -> {
                commands { +ChannelCommand.Init(state.subscribedStreamList, state.allStreamList) }
            }
            //streams
            is ChannelEvent.Ui.LoadStreamList -> {
                state { copy(isLoading = true, error = null) }
                commands { +ChannelCommand.SubscribeOnStreams(event.isSubscribedFragment) }
                commands { +ChannelCommand.GetActualStreamsData(event.isSubscribedFragment) }
            }
            is ChannelEvent.Ui.CreateNewStream -> {
                if (isFieldsNotEmpty(event.streamName, event.description)) {
                    commands {
                        +ChannelCommand.CreateNewStream(
                            event.streamName,
                            event.description
                        )
                    }
                } else {
                    effects { +ChannelEffect.FieldsIsEmpty }
                }
            }
            // topics
            is ChannelEvent.Ui.LoadTopicsOfStream -> {
                commands {
                    +ChannelCommand.SubscribeOnTopics(
                        event.streamModel,
                        event.isSubscribedFragment
                    )
                }
                commands {
                    +ChannelCommand.GetActualTopicsData(event.streamModel)
                }
            }
            is ChannelEvent.Ui.TopicPressed -> {
                commands { +ChannelCommand.TopicPressed(event.streamName, event.topicName) }
            }

            is ChannelEvent.Ui.StreamPressed -> {
                commands { +ChannelCommand.StreamPressed(event.streamName) }
            }
            //filter stream
            is ChannelEvent.Ui.SetupQueryState -> {
                commands {
                    +ChannelCommand.SetupQueryState(
                        event.searchQueryState,
                        event.isSubscribedFragment
                    )
                }
            }
            //Internal
            //streams list
            is ChannelEvent.Internal.AllStreamListLoaded -> {
                state { copy(isLoading = false, error = null, allStreamList = event.list) }
            }
            is ChannelEvent.Internal.SubscribedStreamListLoaded -> {
                state { copy(isLoading = false, error = null, subscribedStreamList = event.list) }
            }
            is ChannelEvent.Internal.ErrorCreateNewStream -> {
                effects { +ChannelEffect.CreateNewStreamError }
            }
            is ChannelEvent.Internal.AllStreamListFiltered -> {
                effects { +ChannelEffect.AllStreamListFiltered(event.list) }
            }
            is ChannelEvent.Internal.SubscribedStreamListFiltered -> {
                effects { +ChannelEffect.SubscribedStreamListFiltered(event.list) }
            }
            is ChannelEvent.Internal.ErrorStreamLoading -> {
                if (event.isSubscribedFragment && state.subscribedStreamList == null
                    || !event.isSubscribedFragment && state.allStreamList == null
                ) {
                    state { copy(isLoading = false, error = event.error) }
                } else {
                    effects { +ChannelEffect.ActualDataLoadingError }
                }
            }
            //topics
            ChannelEvent.Internal.ErrorTopicsOfStreamLoading -> {
                effects { +ChannelEffect.TopicsOfStreamDidNotLoaded }
            }
            ChannelEvent.Internal.SuccessNavigate -> {
                state { copy() }
            }
        }
    }

    private fun isFieldsNotEmpty(name: String, description: String): Boolean {
        if (name.isEmpty() || description.isEmpty()) {
            return false
        }
        return true
    }
}

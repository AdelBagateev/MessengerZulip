package com.example.homework2.channels.presentation.elm

import android.os.Parcelable
import com.example.homework2.channels.domain.model.StreamModel
import com.example.homework2.channels.domain.model.TopicModel
import com.example.homework2.common.adapter.interfaces.DelegateItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.inject.Inject

@Parcelize
data class ChannelState(
    val subscribedStreamList: @RawValue List<DelegateItem>? = null,
    val allStreamList: @RawValue List<DelegateItem>? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false,
) : Parcelable {
    @Inject
    constructor() : this(null, null, null, false)
}

sealed interface ChannelEvent {
    sealed interface Ui : ChannelEvent {
        class LoadStreamList(val isSubscribedFragment: Boolean) : Ui
        class LoadTopicsOfStream(val streamModel: StreamModel, val isSubscribedFragment: Boolean) : Ui

        class TopicPressed(val topic: TopicModel) : Ui
        class SetupQueryState(
            val searchQueryState: MutableSharedFlow<String>,
            val isSubscribedFragment: Boolean
        ) : Ui
        object InitFromChannel : Ui
        object InitFromStream : Ui
    }

    sealed interface Internal : ChannelEvent {
        // stream
        class SubscribedStreamListLoaded(val list: List<DelegateItem>) : Internal
        class AllStreamListLoaded(val list: List<DelegateItem>) : Internal

        class SubscribedStreamListFiltered(val list: List<DelegateItem>) : Internal
        class AllStreamListFiltered(val list: List<DelegateItem>) : Internal

        class ErrorStreamLoading(val error: Throwable, val isSubscribedFragment: Boolean) : Internal

        // stream's topic
        object ErrorTopicsOfStreamLoading : Internal

        //navigation
        object SuccessNavigate : Internal
    }
}

sealed interface ChannelEffect {
    object TopicsOfStreamDidNotLoaded : ChannelEffect
    class SubscribedStreamListFiltered(val list: List<DelegateItem>): ChannelEffect
    class AllStreamListFiltered(val list: List<DelegateItem>): ChannelEffect
    object ActualDataLoadingError : ChannelEffect
}

sealed interface ChannelCommand {
    class SubscribeOnStreams(val isSubscribedFragment: Boolean) : ChannelCommand
    class GetActualStreamsData(val isSubscribedFragment: Boolean) : ChannelCommand
    class SubscribeOnTopics(val streamModel: StreamModel, val isSubscribedFragment: Boolean) : ChannelCommand
    class GetActualTopicsData(val streamModel: StreamModel) : ChannelCommand
    class TopicPressed(val topic: TopicModel) : ChannelCommand

    class SetupQueryState(
        val searchQueryState: MutableSharedFlow<String>,
        val isSubscribedFragment: Boolean
    ) : ChannelCommand
    class Init(
        val subscribedStreamList:  List<DelegateItem>?,
        val allStreamList:  List<DelegateItem>?
    ) : ChannelCommand
}

package com.example.channels.di.store

import androidx.lifecycle.Lifecycle
import com.example.channels.presentation.elm.ChannelCommand
import com.example.channels.presentation.elm.ChannelEffect
import com.example.channels.presentation.elm.ChannelEvent
import com.example.channels.presentation.elm.ChannelState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import vivid.money.elmslie.coroutines.ElmStoreCompat

class ChannelStoreHolderWrapper @AssistedInject constructor(
    val store: ElmStoreCompat<ChannelEvent, ChannelState, ChannelEffect, ChannelCommand>,
    @Assisted val lifecycle: Lifecycle
) {
    val storeHolder = LifecycleAwareStoreHolder(lifecycle) { store }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted lifecycle: Lifecycle): ChannelStoreHolderWrapper
    }
}

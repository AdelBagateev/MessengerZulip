package com.example.homework2.channels.di

import androidx.lifecycle.Lifecycle
import com.example.homework2.channels.presentation.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import vivid.money.elmslie.android.storeholder.StoreHolder
import vivid.money.elmslie.coroutines.ElmStoreCompat

@Module
class ChannelStoreHolderModule {
    @ChannelScope
    @Provides
    fun provideStoreHolder(
        store: ElmStoreCompat<ChannelEvent, ChannelState, ChannelEffect, ChannelCommand>,
        lifecycle: Lifecycle
    ): StoreHolder<ChannelEvent, ChannelEffect, ChannelState> =
        LifecycleAwareStoreHolder(lifecycle) { store }

    @Provides
    fun provideStore(
        actor: ChannelActor,
        channelStateManager: ChannelStateManager,
        channelReducer: ChannelReducer,
    ): ElmStoreCompat<ChannelEvent, ChannelState, ChannelEffect, ChannelCommand> {
        return ElmStoreCompat(
            initialState = channelStateManager.state,
            reducer = channelReducer,
            actor = actor
        )
    }
}

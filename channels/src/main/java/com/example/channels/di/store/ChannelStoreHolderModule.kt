package com.example.channels.di.store

import com.example.channels.presentation.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.coroutines.ElmStoreCompat

@Module
class ChannelStoreHolderModule {
    @Provides
    fun provideStore(
        actor: ChannelActor,
        channelState: ChannelState,
        channelReducer: ChannelReducer,
    ): ElmStoreCompat<ChannelEvent, ChannelState, ChannelEffect, ChannelCommand> {
        return ElmStoreCompat(
            initialState = channelState,
            reducer = channelReducer,
            actor = actor
        )
    }
}

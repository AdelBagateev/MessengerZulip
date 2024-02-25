package com.example.messenger.di.store

import com.example.messenger.presentation.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.coroutines.ElmStoreCompat

@Module
class MessengerStoreHolderModule {
    @Provides
    fun provideStore(
        actor: MessengerActor,
        messengerState: MessengerState,
        messengerReducer: MessengerReducer,
    ): ElmStoreCompat<MessengerEvent, MessengerState, MessengerEffect, MessengerCommand> {
        return ElmStoreCompat(
            initialState = messengerState,
            reducer = messengerReducer,
            actor = actor
        )
    }
}

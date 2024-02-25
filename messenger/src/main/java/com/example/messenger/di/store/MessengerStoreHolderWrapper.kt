package com.example.messenger.di.store

import androidx.lifecycle.Lifecycle
import com.example.messenger.presentation.elm.MessengerCommand
import com.example.messenger.presentation.elm.MessengerEffect
import com.example.messenger.presentation.elm.MessengerEvent
import com.example.messenger.presentation.elm.MessengerState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import vivid.money.elmslie.coroutines.ElmStoreCompat

class MessengerStoreHolderWrapper @AssistedInject constructor(
    val store: ElmStoreCompat<MessengerEvent, MessengerState, MessengerEffect, MessengerCommand>,
    @Assisted val lifecycle: Lifecycle
) {
    val storeHolder = LifecycleAwareStoreHolder(lifecycle) { store }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted lifecycle: Lifecycle): MessengerStoreHolderWrapper
    }
}

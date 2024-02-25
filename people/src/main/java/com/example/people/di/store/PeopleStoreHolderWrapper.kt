package com.example.people.di.store

import androidx.lifecycle.Lifecycle
import com.example.people.presentation.elm.PeopleCommand
import com.example.people.presentation.elm.PeopleEffect
import com.example.people.presentation.elm.PeopleEvent
import com.example.people.presentation.elm.PeopleState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import vivid.money.elmslie.coroutines.ElmStoreCompat

class PeopleStoreHolderWrapper @AssistedInject constructor(
    val store: ElmStoreCompat<PeopleEvent, PeopleState, PeopleEffect, PeopleCommand>,
    @Assisted val lifecycle: Lifecycle
) {
    val storeHolder = LifecycleAwareStoreHolder(lifecycle) { store }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted lifecycle: Lifecycle): PeopleStoreHolderWrapper
    }
}

package com.example.homework2.people.di

import androidx.lifecycle.Lifecycle
import com.example.homework2.people.presentation.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import vivid.money.elmslie.android.storeholder.StoreHolder
import vivid.money.elmslie.coroutines.ElmStoreCompat

@Module
class PeopleStoreHolderModule {

    @Provides
    fun provideStoreHolder(
        store: ElmStoreCompat<PeopleEvent, PeopleState, PeopleEffect, PeopleCommand>,
        lifecycle: Lifecycle
    ): StoreHolder<PeopleEvent, PeopleEffect, PeopleState> =
        LifecycleAwareStoreHolder(lifecycle) { store }


    @Provides
    fun provideStore(
        actor: PeopleActor,
        peopleStateManager: PeopleStateManager,
        peopleReducer: PeopleReducer,
    ): ElmStoreCompat<PeopleEvent, PeopleState, PeopleEffect, PeopleCommand> {
        return ElmStoreCompat(
            initialState = peopleStateManager.state,
            reducer = peopleReducer,
            actor = actor
        )
    }
}

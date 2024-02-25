package com.example.people.di.store

import com.example.people.presentation.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.coroutines.ElmStoreCompat

@Module
class PeopleStoreHolderModule {
    @Provides
    fun provideStore(
        actor: PeopleActor,
        peopleState: PeopleState,
        peopleReducer: PeopleReducer,
    ): ElmStoreCompat<PeopleEvent, PeopleState, PeopleEffect, PeopleCommand> {
        return ElmStoreCompat(
            initialState = peopleState,
            reducer = peopleReducer,
            actor = actor
        )
    }
}

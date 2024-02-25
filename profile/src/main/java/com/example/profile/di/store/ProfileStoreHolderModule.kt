package com.example.profile.di.store

import com.example.profile.presentation.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.coroutines.ElmStoreCompat

@Module
class ProfileStoreHolderModule {
    @Provides
    fun provideStore(
        actor: ProfileActor,
        profileState: ProfileState,
        profileReducer: ProfileReducer,
    ): ElmStoreCompat<ProfileEvent, ProfileState, ProfileEffect, ProfileCommand> {
        return ElmStoreCompat(
            initialState = profileState,
            reducer = profileReducer,
            actor = actor
        )
    }
}

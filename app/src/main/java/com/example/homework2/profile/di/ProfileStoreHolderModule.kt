package com.example.homework2.profile.di

import androidx.lifecycle.Lifecycle
import com.example.homework2.profile.presentation.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import vivid.money.elmslie.android.storeholder.StoreHolder
import vivid.money.elmslie.coroutines.ElmStoreCompat

@Module
class ProfileStoreHolderModule {

    @Provides
    fun provideStoreHolder(
        store: ElmStoreCompat<ProfileEvent, ProfileState, ProfileEffect, ProfileCommand>,
        lifecycle: Lifecycle
    ): StoreHolder<ProfileEvent, ProfileEffect, ProfileState> =
        LifecycleAwareStoreHolder(lifecycle) { store }


    @Provides
    fun provideStore(
        actor: ProfileActor,
        profileStateManager: ProfileStateManager,
        profileReducer: ProfileReducer,
    ): ElmStoreCompat<ProfileEvent, ProfileState, ProfileEffect, ProfileCommand> {
        return ElmStoreCompat(
            initialState = profileStateManager.state,
            reducer = profileReducer,
            actor = actor
        )
    }
}

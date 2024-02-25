package com.example.profile.di.store

import androidx.lifecycle.Lifecycle
import com.example.profile.presentation.elm.ProfileCommand
import com.example.profile.presentation.elm.ProfileEffect
import com.example.profile.presentation.elm.ProfileEvent
import com.example.profile.presentation.elm.ProfileState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import vivid.money.elmslie.coroutines.ElmStoreCompat

class ProfileStoreHolderWrapper @AssistedInject constructor(
    val store: ElmStoreCompat<ProfileEvent, ProfileState, ProfileEffect, ProfileCommand>,
    @Assisted val lifecycle: Lifecycle
) {
    val storeHolder = LifecycleAwareStoreHolder(lifecycle) { store }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted lifecycle: Lifecycle): ProfileStoreHolderWrapper
    }
}

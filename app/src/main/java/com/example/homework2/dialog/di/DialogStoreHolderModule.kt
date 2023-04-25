package com.example.homework2.dialog.di

import androidx.lifecycle.Lifecycle
import com.example.homework2.dialog.presentation.elm.*
import dagger.Module
import dagger.Provides
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import vivid.money.elmslie.android.storeholder.StoreHolder
import vivid.money.elmslie.coroutines.ElmStoreCompat

@Module
class DialogStoreHolderModule {

    @Provides
    fun provideStoreHolder(
        store: ElmStoreCompat<DialogEvent, DialogState, DialogEffect, DialogCommand>,
        lifecycle: Lifecycle
    ): StoreHolder<DialogEvent, DialogEffect, DialogState> =
        LifecycleAwareStoreHolder(lifecycle) { store }


    @Provides
    fun provideStore(
        actor: DialogActor,
        dialogStateManager: DialogStateManager,
        dialogReducer: DialogReducer,
    ): ElmStoreCompat<DialogEvent, DialogState, DialogEffect, DialogCommand> {
        return ElmStoreCompat(
            initialState = dialogStateManager.state,
            reducer = dialogReducer,
            actor = actor
        )
    }
}

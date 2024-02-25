package com.example.messenger.di

import com.example.common.di.holder.FeatureComponentHolder
import com.example.messenger.di.deps.MessengerDepsStore

object MessengerComponentHolder : FeatureComponentHolder<MessengerComponent>() {
    override fun build(): MessengerComponent =
        DaggerMessengerComponent.builder()
            .dependencies(MessengerDepsStore.deps)
            .build()
}



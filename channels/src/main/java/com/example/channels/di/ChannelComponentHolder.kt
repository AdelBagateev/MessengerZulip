package com.example.channels.di

import com.example.channels.di.deps.ChannelDepsStore
import com.example.common.di.holder.FeatureComponentHolder

object ChannelComponentHolder : FeatureComponentHolder<ChannelComponent>() {
    override fun build(): ChannelComponent =
        DaggerChannelComponent.builder()
            .appComponent(ChannelDepsStore.deps)
            .build()
}

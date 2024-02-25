package com.example.channels.di

import com.example.channels.di.bind.ChannelBindModule
import com.example.channels.di.data.ChannelApiModule
import com.example.channels.di.data.ChannelDaoModule
import com.example.channels.di.deps.ChannelDeps
import com.example.channels.di.store.ChannelStoreHolderModule
import com.example.channels.presentation.ui.ChannelFragment
import com.example.channels.presentation.ui.StreamsFragment
import com.example.common.di.holder.DiComponent
import dagger.Component
import dagger.Module

@ChannelScope
@Component(dependencies = [ChannelDeps::class], modules = [ChannelModule::class])
interface ChannelComponent : DiComponent {
    fun inject(channelFragment: ChannelFragment)
    fun inject(streamsFragment: StreamsFragment)

    @Component.Builder
    interface Builder {
        fun appComponent(channelDeps: ChannelDeps): Builder
        fun build(): ChannelComponent
    }
}

@Module(
    includes = [ChannelBindModule::class, ChannelStoreHolderModule::class,
        ChannelApiModule::class, ChannelDaoModule::class]
)
class ChannelModule

package com.example.homework2.channels.di

import androidx.lifecycle.Lifecycle
import com.example.homework2.channels.presentation.ui.ChannelFragment
import com.example.homework2.channels.presentation.ui.StreamsFragment
import com.example.homework2.global_di.AppComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module

@ChannelScope
@Component(dependencies = [AppComponent::class], modules = [ChannelModule::class])
interface ChannelComponent {
    fun inject(channelFragment: ChannelFragment)
    fun inject(streamsFragment: StreamsFragment)

    fun lifecycle(lifecycle: Lifecycle): Lifecycle

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun lifecycle(lifecycle: Lifecycle): Builder
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): ChannelComponent
    }
}

@Module(includes = [ChannelBindModule::class, ChannelStoreHolderModule::class])
class ChannelModule

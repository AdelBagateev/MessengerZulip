package com.example.homework2.profile.di

import androidx.lifecycle.Lifecycle
import com.example.homework2.global_di.AppComponent
import com.example.homework2.profile.presentation.ui.ProfileFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module

@ProfileScope
@Component(modules = [ProfileModule::class], dependencies = [AppComponent::class])
interface ProfileComponent {
    fun inject(profileFragment: ProfileFragment)

    fun lifecycle(lifecycle: Lifecycle): Lifecycle

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun lifecycle(lifecycle: Lifecycle): Builder
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): ProfileComponent
    }
}

@Module(includes = [ProfileBindModule::class, ProfileStoreHolderModule::class])
class ProfileModule



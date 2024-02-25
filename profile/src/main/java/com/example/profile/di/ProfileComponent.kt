package com.example.profile.di

import com.example.common.di.holder.DiComponent
import com.example.profile.di.bind.ProfileBindModule
import com.example.profile.di.data.ProfileApiModule
import com.example.profile.di.data.ProfileDaoModule
import com.example.profile.di.deps.ProfileDeps
import com.example.profile.di.store.ProfileStoreHolderModule
import com.example.profile.presentation.ui.ProfileFragment
import dagger.Component
import dagger.Module

@ProfileScope
@Component(modules = [ProfileModule::class], dependencies = [ProfileDeps::class])
interface ProfileComponent : DiComponent {
    fun inject(profileFragment: ProfileFragment)

    @Component.Builder
    interface Builder {
        fun dependencies(profileDeps: ProfileDeps): Builder
        fun build(): ProfileComponent
    }
}

@Module(
    includes = [ProfileBindModule::class, ProfileStoreHolderModule::class,
        ProfileApiModule::class, ProfileDaoModule::class]
)
class ProfileModule

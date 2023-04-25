package com.example.homework2.people.di

import androidx.lifecycle.Lifecycle
import com.example.homework2.global_di.AppComponent
import com.example.homework2.people.presentation.ui.PeopleFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module

@PeopleScope
@Component(dependencies = [AppComponent::class], modules = [PeopleModule::class])
interface PeopleComponent {
    fun inject(peopleFragment: PeopleFragment)

    fun lifecycle(lifecycle: Lifecycle): Lifecycle

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun lifecycle(lifecycle: Lifecycle): Builder
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): PeopleComponent
    }
}

@Module(includes = [PeopleBindModule::class, PeopleStoreHolderModule::class])
class PeopleModule

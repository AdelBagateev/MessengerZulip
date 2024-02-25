package com.example.people.di

import com.example.common.di.holder.DiComponent
import com.example.people.di.bind.PeopleBindModule
import com.example.people.di.data.PeopleApiModule
import com.example.people.di.data.PeopleDaoModule
import com.example.people.di.deps.PeopleDeps
import com.example.people.di.store.PeopleStoreHolderModule
import com.example.people.presentation.ui.PeopleFragment
import dagger.Component
import dagger.Module

@PeopleScope
@Component(dependencies = [PeopleDeps::class], modules = [PeopleModule::class])
interface PeopleComponent : DiComponent {
    fun inject(peopleFragment: PeopleFragment)

    @Component.Builder
    interface Builder {
        fun dependencies(peopleDeps: PeopleDeps): Builder
        fun build(): PeopleComponent
    }
}

@Module(
    includes = [PeopleBindModule::class, PeopleStoreHolderModule::class,
        PeopleApiModule::class, PeopleDaoModule::class]
)
class PeopleModule

package com.example.people.di.bind

import dagger.Module

@Module(
    includes = [PeoplePresenterLayerBindModule::class, PeopleDataLayerBindModule::class,
        PeopleDomainLayerBindModule::class]
)
class PeopleBindModule

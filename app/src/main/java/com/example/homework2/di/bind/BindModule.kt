package com.example.homework2.di.bind

import dagger.Module

@Module(
    includes = [
        ProfileDepsBindModule::class, ChannelDepsBindModule::class,
        MessengerDepsBindModuleModule::class, PeopleDepsBindModule::class]
)
class BindModule

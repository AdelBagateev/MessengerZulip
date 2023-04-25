package com.example.homework2.global_di.apis

import dagger.Module

@Module(
    includes = [ChannelApiModule::class, DialogApiModule::class,
        PeopleApiModule::class, ProfileApiModule::class]
)
class CoreApiModule

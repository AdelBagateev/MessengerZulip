package com.example.channels.di.bind


import dagger.Module

@Module(includes = [ChannelPresenterLayerBindModule::class, ChannelDataLayerBindModule::class, ChannelDomainLayerBindModule::class])
class ChannelBindModule

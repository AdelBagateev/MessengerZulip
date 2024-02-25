package com.example.messenger.di.bind

import dagger.Module

@Module(
    includes = [MessengerPresenterLayerBindModule::class, MessengerDataLayerBindModule::class,
        MessengerDomainLayerBindModule::class]
)
class MessengerBindModule

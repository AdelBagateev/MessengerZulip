package com.example.profile.di.bind

import dagger.Module

@Module(includes = [ProfileDataLayerBindModule::class, ProfileDomainLayerBindModule::class])
internal interface ProfileBindModule

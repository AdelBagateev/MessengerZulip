package com.example.profile.di.data

import com.example.profile.data.datasource.remote.ProfileApi
import com.example.profile.di.ProfileScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ProfileApiModule {
    @ProfileScope
    @Provides
    internal fun provideProfileApi(retrofit: Retrofit): ProfileApi {
        return retrofit.create(ProfileApi::class.java)
    }
}


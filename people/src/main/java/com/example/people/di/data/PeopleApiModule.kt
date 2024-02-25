package com.example.people.di.data

import com.example.people.data.datasource.remote.PeopleApi
import com.example.people.di.PeopleScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class PeopleApiModule {
    @PeopleScope
    @Provides
    internal fun providePeopleApi(retrofit: Retrofit): PeopleApi {
        return retrofit.create(PeopleApi::class.java)
    }
}

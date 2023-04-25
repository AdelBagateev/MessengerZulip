package com.example.homework2.global_di.apis

import com.example.homework2.people.data.datasource.remote.PeopleApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class PeopleApiModule {
    @Singleton
    @Provides
    fun providePeopleApi(retrofit: Retrofit): PeopleApi {
        return retrofit.create(PeopleApi::class.java)
    }
}

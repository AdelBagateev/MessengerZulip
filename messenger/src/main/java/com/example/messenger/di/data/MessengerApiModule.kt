package com.example.messenger.di.data

import com.example.messenger.data.datasource.remote.MessengerApi
import com.example.messenger.di.MessengerScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MessengerApiModule {
    @MessengerScope
    @Provides
    internal fun provideDialogApi(retrofit: Retrofit): MessengerApi {
        return retrofit.create(MessengerApi::class.java)
    }
}

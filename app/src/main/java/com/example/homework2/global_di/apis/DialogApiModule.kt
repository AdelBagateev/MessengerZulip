package com.example.homework2.global_di.apis

import com.example.homework2.dialog.data.datasource.remote.DialogApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class DialogApiModule {
    @Singleton
    @Provides
    fun provideDialogApi(retrofit: Retrofit): DialogApi {
        return retrofit.create(DialogApi::class.java)
    }
}

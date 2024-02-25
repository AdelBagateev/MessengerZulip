package com.example.messenger.di.bind

import com.example.messenger.presentation.mapper.MessageDomainToUiMapper
import com.example.messenger.presentation.mapper.MessageDomainToUiMapperImpl
import dagger.Binds
import dagger.Module

@Module
internal interface MessengerPresenterLayerBindModule {
    @Binds
    fun bindDialogMapperPresenterImplToDialogMapperPresenter(
        dialogMapperPresenterImpl: MessageDomainToUiMapperImpl
    ): MessageDomainToUiMapper
}

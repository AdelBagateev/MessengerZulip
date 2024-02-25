package com.example.messenger.di.bind

import com.example.messenger.data.MessageRepositoryImpl
import com.example.messenger.data.mapper.MessageApiToDbMapper
import com.example.messenger.data.mapper.MessageApiToDbMapperImpl
import com.example.messenger.data.mapper.MessageDbToDomainMapper
import com.example.messenger.data.mapper.MessageDbToDomainMapperImpl
import com.example.messenger.domain.MessageRepository
import dagger.Binds
import dagger.Module

@Module
internal interface MessengerDataLayerBindModule {
    @Binds
    fun bindMessageRepositoryImplToMessageRepository(
        messageRepositoryImpl: MessageRepositoryImpl
    ): MessageRepository

    @Binds
    fun bindMessageApiToDbMapperImplToMessageApiToDbMapper(
        messageApiToDbMapper: MessageApiToDbMapperImpl
    ): MessageApiToDbMapper

    @Binds
    fun bindMessageDbToPresenterMapperImplToMessageDbToPresenterMapper(
        messageDbToPresenterMapper: MessageDbToDomainMapperImpl
    ): MessageDbToDomainMapper
}

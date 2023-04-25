package com.example.homework2.dialog.di

import com.example.homework2.dialog.data.MessageRepositoryImpl
import com.example.homework2.dialog.domain.MessageRepository
import com.example.homework2.dialog.domain.usecase.message.*
import com.example.homework2.dialog.domain.usecase.reaction.AddReactionInMessageUseCase
import com.example.homework2.dialog.domain.usecase.reaction.AddReactionInMessageUseCaseImpl
import com.example.homework2.dialog.domain.usecase.reaction.RemoveReactionInMessageUseCase
import com.example.homework2.dialog.domain.usecase.reaction.RemoveReactionInMessageUseCaseImpl
import com.example.homework2.dialog.utils.DialogMapperData
import com.example.homework2.dialog.utils.DialogMapperDataImpl
import com.example.homework2.dialog.utils.DialogMapperPresenter
import com.example.homework2.dialog.utils.DialogMapperPresenterImpl
import dagger.Binds
import dagger.Module

@Module
internal interface DialogBindModule {
    @Binds
    fun bindAddReactionInMessageUseCaseImplAddReactionInMessageUseCase(
        addReactionInMessageUseCaseImpl: AddReactionInMessageUseCaseImpl
    ): AddReactionInMessageUseCase

    @Binds
    fun bindLoadMessageByStreamAndTopicUseCaseImplToLoadMessageByStreamAndTopicUseCase(
        loadMessageByStreamAndTopicUseCaseImpl: LoadMessageByStreamAndTopicUseCaseImpl
    ): LoadMessageByStreamAndTopicUseCase
    @Binds
    fun bindGetMessageByStreamAndTopicUseCaseImplToGetMessageByStreamAndTopicUseCase(
        getMessageByStreamAndTopicUseCaseImpl: SubscribeOnMessageByStreamAndTopicUseCaseImpl
    ): SubscribeOnMessageByStreamAndTopicUseCase

    @Binds
    fun bindRemoveReactionInMessageUseCaseImplToRemoveReactionInMessageUseCase(
        removeReactionInMessageUseCaseImpl: RemoveReactionInMessageUseCaseImpl
    ): RemoveReactionInMessageUseCase

    @Binds
    fun bindSendMessageUseCaseImplToSendMessageUseCase(
        sendMessageUseCaseImpl: SendMessageUseCaseImpl
    ): SendMessageUseCase
    @Binds
    fun bindDeleteOldMessagesUseCaseImplToDeleteOldMessagesUseCase(
        deleteOldMessagesUseCaseImpl: DeleteOldMessagesUseCaseImpl
    ): DeleteOldMessagesUseCase
    @Binds
    fun bindSendMediaMessageUseCaseImplToSendMediaMessageUseCase(
        sendMediaMessageUseCase: SendMediaMessageUseCaseImpl
    ): SendMediaMessageUseCase

    @Binds
    fun bindMessageRepositoryImplToMessageRepository(
        messageRepositoryImpl: MessageRepositoryImpl
    ): MessageRepository

    @Binds
    fun bindDialogMapperPresenterImplToDialogMapperPresenter(
        dialogMapperPresenterImpl: DialogMapperPresenterImpl
    ): DialogMapperPresenter

    @Binds
    fun bindDialogMapperDataImplToDialogMapperData(
        dialogMapperDataImpl: DialogMapperDataImpl
    ): DialogMapperData
}

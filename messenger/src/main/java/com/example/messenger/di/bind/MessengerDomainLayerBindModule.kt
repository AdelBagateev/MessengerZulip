package com.example.messenger.di.bind

import com.example.messenger.domain.usecase.message.*
import com.example.messenger.domain.usecase.reaction.AddReactionInMessageUseCase
import com.example.messenger.domain.usecase.reaction.AddReactionInMessageUseCaseImpl
import com.example.messenger.domain.usecase.reaction.RemoveReactionInMessageUseCase
import com.example.messenger.domain.usecase.reaction.RemoveReactionInMessageUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
internal interface MessengerDomainLayerBindModule {
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
        sendMediaMessageUseCaseImpl: SendMediaMessageUseCaseImpl
    ): SendMediaMessageUseCase

    @Binds
    fun bindDeleteMessageByIdUseCaseImplToDeleteMessageByIdUseCase(
        deleteMessageByIdUseCaseImpl: DeleteMessageByIdUseCaseImpl
    ): DeleteMessageByIdUseCase

    @Binds
    fun bindUpdateMessageUseCaseImplToUpdateMessageUseCase(
        updateMessageUseCaseImpl: UpdateMessageUseCaseImpl
    ): UpdateMessageUseCase
}

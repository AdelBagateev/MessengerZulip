package com.example.channels.domain.stream.usecase

import com.example.channels.domain.model.StreamModel
import com.example.channels.domain.stream.StreamsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SubscribeOnStreamUseCaseImpl @Inject constructor(
    private val streamsRepository: StreamsRepository
) : SubscribeOnStreamUseCase {
    override operator fun invoke(isSubscribedStreams: Boolean): Flow<List<StreamModel>> =
        streamsRepository.subscribeOnStreams(isSubscribedStreams)
}

interface SubscribeOnStreamUseCase {
    operator fun invoke(isSubscribedStreams: Boolean): Flow<List<StreamModel>>
}

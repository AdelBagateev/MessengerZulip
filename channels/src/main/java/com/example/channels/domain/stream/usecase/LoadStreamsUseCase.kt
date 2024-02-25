package com.example.channels.domain.stream.usecase

import com.example.channels.domain.stream.StreamsRepository
import javax.inject.Inject

internal class LoadStreamsUseCaseImpl @Inject constructor(
    private val streamsRepository: StreamsRepository
) : LoadStreamsUseCase {
    override suspend operator fun invoke(isSubscribedStreams: Boolean) =
        streamsRepository.loadStreams(isSubscribedStreams)
}

interface LoadStreamsUseCase {
    suspend operator fun invoke(isSubscribedStreams: Boolean)
}

package com.example.channels.domain.stream.usecase

import com.example.channels.domain.stream.StreamsRepository
import javax.inject.Inject

internal class CreateNewStreamUseCaseImpl @Inject constructor(
    private val streamsRepository: StreamsRepository
) : CreateNewStreamUseCase {
    override suspend operator fun invoke(streamName: String, description: String) =
        streamsRepository.createNewStream(streamName, description)
}

interface CreateNewStreamUseCase {
    suspend operator fun invoke(streamName: String, description: String)
}

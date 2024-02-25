package com.example.profile.domain.usecase

import com.example.profile.domain.ProfileRepository
import javax.inject.Inject

internal class LoadUserByIdUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : LoadUserByIdUseCase {
    override suspend operator fun invoke(id: Int) =
        repository.getActualDataOfUser(id)
}

interface LoadUserByIdUseCase {
    suspend operator fun invoke(id: Int)
}

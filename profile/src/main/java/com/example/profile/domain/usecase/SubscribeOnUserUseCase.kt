package com.example.profile.domain.usecase

import com.example.profile.domain.ProfileRepository
import com.example.profile.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SubscribeOnUserUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : SubscribeOnUserUseCase {
    override operator fun invoke(id: Int): Flow<UserModel> =
        repository.subscribeOnUser(id)
}

interface SubscribeOnUserUseCase {
    operator fun invoke(id: Int): Flow<UserModel>
}

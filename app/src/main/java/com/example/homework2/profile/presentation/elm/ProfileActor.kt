package com.example.homework2.profile.presentation.elm

import com.example.homework2.common.catchCancellationException
import com.example.homework2.profile.domain.LoadUserByIdUseCase
import com.example.homework2.profile.domain.SubscribeOnUserUseCase
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

class ProfileActor @Inject constructor(
    private val subscribeOnUserUseCase: SubscribeOnUserUseCase,
    private val loadUserByIdUseCase: LoadUserByIdUseCase,
    private val router: Router,
) : Actor<ProfileCommand, ProfileEvent> {
    override fun execute(command: ProfileCommand): Flow<ProfileEvent> {
        return when (command) {
            is ProfileCommand.SubscribeOnUser -> subscribeOnUser(command.id)
            ProfileCommand.BtnBackPressed -> navigate()
            is ProfileCommand.GetActualUserData -> getActualUserData(command.id)
        }
    }

    private fun getActualUserData(id: Int): Flow<ProfileEvent> {
        return flow{
            emit(loadUserByIdUseCase(id))
        }.mapEvents { error ->
                catchCancellationException(error)
                Timber.e(error.message)
                ProfileEvent.Internal.ErrorLoading(error)
        }.flowOn(Dispatchers.Default)
    }

    private fun navigate(): Flow<ProfileEvent> {
        router.exit()
        return flow { emit(ProfileEvent.Internal.SuccessNavigate) }
            .flowOn(Dispatchers.Default)
    }
    private fun subscribeOnUser(id: Int): Flow<ProfileEvent> {
        return subscribeOnUserUseCase(id)
                .distinctUntilChanged()
                .mapEvents(
                    { user ->
                        ProfileEvent.Internal.UserLoaded(user)
                    },
                    { error ->
                        catchCancellationException(error)
                        Timber.e(error.message)
                        ProfileEvent.Internal.ErrorLoading(error)
                    }
                )
                .flowOn(Dispatchers.Default)
    }
}

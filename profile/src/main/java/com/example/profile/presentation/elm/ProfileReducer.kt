package com.example.profile.presentation.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject

class ProfileReducer @Inject constructor() :
    DslReducer<ProfileEvent, ProfileState, ProfileEffect, ProfileCommand>() {
    override fun Result.reduce(event: ProfileEvent): Any {
        return when (event) {
            is ProfileEvent.Ui.LoadUser -> {
                state { copy(isLoading = true, error = null, user = null) }
                commands { +ProfileCommand.SubscribeOnUser(event.id) }
                commands { +ProfileCommand.GetActualUserData(event.id) }
            }
            ProfileEvent.Ui.BtnBackPressed -> {
                commands { +ProfileCommand.BtnBackPressed }
            }
            ProfileEvent.Ui.Init -> {}
            is ProfileEvent.Internal.UserLoaded -> {
                state { copy(isLoading = false, error = null, user = event.user) }
            }
            is ProfileEvent.Internal.ErrorLoading -> {
                if (state.user == null) {
                    state { copy(isLoading = false, error = event.error) }
                } else {
                    effects { +ProfileEffect.ErrorGetActualDataFromNetwork }
                }
            }
            ProfileEvent.Internal.SuccessNavigate -> {
                state { copy() }
            }
        }
    }
}

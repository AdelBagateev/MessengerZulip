package com.example.profile.presentation.elm

import android.os.Parcelable
import com.example.profile.domain.model.UserModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.inject.Inject

@Parcelize
data class ProfileState(
    val user: @RawValue UserModel? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false,
) : Parcelable {
    @Inject
    constructor() : this(null, null, false)
}

sealed interface ProfileEvent {
    sealed interface Ui : ProfileEvent {
        class LoadUser(val id: Int) : Ui
        object BtnBackPressed : Ui
        object Init : Ui
    }

    sealed interface Internal : ProfileEvent {
        class UserLoaded(val user: UserModel) : Internal
        class ErrorLoading(val error: Throwable) : Internal

        object SuccessNavigate : Internal
    }
}

sealed interface ProfileEffect {
    object ErrorGetActualDataFromNetwork : ProfileEffect
}

sealed interface ProfileCommand {
    class SubscribeOnUser(val id: Int) : ProfileCommand
    class GetActualUserData(val id: Int) : ProfileCommand
    object BtnBackPressed : ProfileCommand
}

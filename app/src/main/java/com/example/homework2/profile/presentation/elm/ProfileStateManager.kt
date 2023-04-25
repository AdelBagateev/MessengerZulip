package com.example.homework2.profile.presentation.elm

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileStateManager @Inject constructor(
    profileState: ProfileState
) {
    var state: ProfileState = profileState
}

package com.example.homework2.dialog.presentation.elm

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DialogStateManager @Inject constructor(
    dialogState: DialogState
) {
    var state: DialogState = dialogState
}

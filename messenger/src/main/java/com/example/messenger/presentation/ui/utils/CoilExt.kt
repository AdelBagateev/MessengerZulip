package com.example.messenger.presentation.ui.utils

import coil.request.ImageRequest
import com.example.common.BuildConfig

fun ImageRequest.Builder.addAuthHeader() {
    addHeader("Authorization", BuildConfig.API_KEY)
}

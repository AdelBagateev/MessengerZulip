package com.example.homework2.dialog.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DateModel(
    val date: String,
) : Parcelable

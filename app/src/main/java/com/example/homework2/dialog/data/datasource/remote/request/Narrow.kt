package com.example.homework2.dialog.data.datasource.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class Narrow(
    val operator: String,
    val operand: String
)

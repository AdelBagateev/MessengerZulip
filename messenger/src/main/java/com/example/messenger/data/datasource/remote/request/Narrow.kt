package com.example.messenger.data.datasource.remote.request

import kotlinx.serialization.Serializable

@Serializable
class Narrow(
    val operator: String,
    val operand: String
)

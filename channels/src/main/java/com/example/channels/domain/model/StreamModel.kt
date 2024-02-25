package com.example.channels.domain.model


data class StreamModel(
    val id: Int,
    val name: String,
    val isChosen: Boolean = false
)

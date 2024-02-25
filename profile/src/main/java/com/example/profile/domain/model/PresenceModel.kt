package com.example.profile.domain.model

data class PresenceModel(
    val status: String,
) {
    companion object {
        const val OFFLINE_STATUS = "offline"
        const val ONLINE_STATUS = "active"
        const val IDLE_STATUS = "idle"
    }
}

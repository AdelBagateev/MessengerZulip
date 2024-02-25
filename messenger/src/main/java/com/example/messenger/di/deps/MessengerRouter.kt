package com.example.messenger.di.deps

interface MessengerRouter {
    fun navigateToMessenger(streamName: String, topicName: String?)
    fun exit()
}

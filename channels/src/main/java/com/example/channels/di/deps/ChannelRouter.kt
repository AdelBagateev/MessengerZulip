package com.example.channels.di.deps

interface ChannelRouter {
    fun navigateToMessenger(streamName: String, topicName: String?)
}

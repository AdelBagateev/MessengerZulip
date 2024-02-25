package com.example.messenger.stub

import com.example.messenger.di.deps.MessengerRouter

class MessengerRouterStub : MessengerRouter {
    override fun navigateToMessenger(streamName: String, topicName: String?) {}

    override fun exit() {}
}

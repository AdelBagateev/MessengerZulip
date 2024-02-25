package com.example.homework2.di.navigation.wrappers

import com.example.channels.di.deps.ChannelRouter
import com.example.homework2.Screens
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class ChannelRouterWrapper @Inject constructor(
    private val router: Router
) : ChannelRouter {
    override fun navigateToMessenger(streamName: String, topicName: String?) {
        router.navigateTo(Screens.Messenger(streamName, topicName))
    }
}

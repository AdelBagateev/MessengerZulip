package com.example.homework2.di.navigation.wrappers

import com.example.homework2.Screens
import com.example.messenger.di.deps.MessengerRouter
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MessengerRouterWrapper @Inject constructor(
    private val router: Router
) : MessengerRouter {

    override fun navigateToMessenger(streamName: String, topicName: String?) {
        router.navigateTo(Screens.Messenger(streamName, topicName))
    }

    override fun exit() {
        router.exit()
    }
}

package com.example.messenger.di.deps

import com.example.common.di.deps.CommonDeps

interface MessengerDeps : CommonDeps {
    val messageDatabase: MessageDatabase
    val messengerRouter: MessengerRouter
}

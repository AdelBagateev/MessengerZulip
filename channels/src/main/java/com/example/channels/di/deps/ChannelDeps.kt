package com.example.channels.di.deps

import com.example.common.di.deps.CommonDeps

interface ChannelDeps : CommonDeps {
    val channelDatabase: ChannelDatabase
    val channelRouter: ChannelRouter
}

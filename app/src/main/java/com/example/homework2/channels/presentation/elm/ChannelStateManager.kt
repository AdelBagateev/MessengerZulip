package com.example.homework2.channels.presentation.elm

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChannelStateManager @Inject constructor(
    channelState: ChannelState
) {
    var state: ChannelState = channelState
}

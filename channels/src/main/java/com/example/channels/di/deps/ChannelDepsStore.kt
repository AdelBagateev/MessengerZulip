package com.example.channels.di.deps

import androidx.annotation.RestrictTo
import kotlin.properties.Delegates

object ChannelDepsStore : ChannelDepsProvider {

    override var deps: ChannelDeps by Delegates.notNull()
}

interface ChannelDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: ChannelDeps

    companion object : ChannelDepsProvider by ChannelDepsStore
}

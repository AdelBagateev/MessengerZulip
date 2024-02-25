package com.example.messenger.di.deps

import androidx.annotation.RestrictTo
import kotlin.properties.Delegates

object MessengerDepsStore : MessengerDepsProvider {

    override var deps: MessengerDeps by Delegates.notNull()
}

interface MessengerDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: MessengerDeps

    companion object : MessengerDepsProvider by MessengerDepsStore
}

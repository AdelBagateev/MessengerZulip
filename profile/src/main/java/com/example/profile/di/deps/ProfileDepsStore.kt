package com.example.profile.di.deps

import androidx.annotation.RestrictTo
import kotlin.properties.Delegates

object ProfileDepsStore : ProfileDepsProvider {

    override var deps: ProfileDeps by Delegates.notNull()
}

interface ProfileDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: ProfileDeps

    companion object : ProfileDepsProvider by ProfileDepsStore
}

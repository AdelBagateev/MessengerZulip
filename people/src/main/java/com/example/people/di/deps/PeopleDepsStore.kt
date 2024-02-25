package com.example.people.di.deps

import androidx.annotation.RestrictTo
import kotlin.properties.Delegates

object PeopleDepsStore : PeopleDepsProvider {

    override var deps: PeopleDeps by Delegates.notNull()
}

interface PeopleDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: PeopleDeps

    companion object : PeopleDepsProvider by PeopleDepsStore
}

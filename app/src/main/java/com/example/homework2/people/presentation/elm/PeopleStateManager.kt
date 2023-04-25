package com.example.homework2.people.presentation.elm

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleStateManager @Inject constructor(
    peopleState: PeopleState
) {
    var state: PeopleState = peopleState
}

package com.example.people.di.deps

interface PeopleRouter {
    fun navigateToProfile(userId: Int, fromPeopleFragment: Boolean)
}

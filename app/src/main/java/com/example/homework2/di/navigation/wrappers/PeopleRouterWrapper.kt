package com.example.homework2.di.navigation.wrappers

import com.example.homework2.Screens
import com.example.people.di.deps.PeopleRouter
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class PeopleRouterWrapper @Inject constructor(
    private val router: Router
) : PeopleRouter {
    override fun navigateToProfile(userId: Int, fromPeopleFragment: Boolean) {
        router.navigateTo(Screens.Profile(userId, fromPeopleFragment))
    }
}

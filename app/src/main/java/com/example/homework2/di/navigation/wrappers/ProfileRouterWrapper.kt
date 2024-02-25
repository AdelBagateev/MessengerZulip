package com.example.homework2.di.navigation.wrappers

import com.example.profile.di.deps.ProfileRouter
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class ProfileRouterWrapper @Inject constructor(
    private val router: Router
) : ProfileRouter {
    override fun exit() {
        router.exit()
    }
}

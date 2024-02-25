package com.example.profile.di.deps

import com.example.common.di.deps.CommonDeps


interface ProfileDeps : CommonDeps {
    val userDatabase: UserDatabase
    val profileRouter: ProfileRouter
}

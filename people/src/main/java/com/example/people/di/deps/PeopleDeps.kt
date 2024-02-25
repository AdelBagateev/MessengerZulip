package com.example.people.di.deps

import com.example.common.di.deps.CommonDeps

interface PeopleDeps : CommonDeps {
    val peopleDatabase: PeopleDatabase
    val peopleRouter: PeopleRouter
}

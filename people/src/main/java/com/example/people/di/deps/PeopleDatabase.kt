package com.example.people.di.deps

import com.example.people.data.datasource.local.PeopleDao

interface PeopleDatabase {
    fun peopleDao(): PeopleDao
}

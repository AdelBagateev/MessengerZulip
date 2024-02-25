package com.example.people.di.data

import com.example.people.data.datasource.local.PeopleDao
import com.example.people.di.PeopleScope
import com.example.people.di.deps.PeopleDatabase
import dagger.Module
import dagger.Provides

@Module
class PeopleDaoModule {
    @PeopleScope
    @Provides
    fun providePeopleDao(db: PeopleDatabase): PeopleDao =
        db.peopleDao()
}

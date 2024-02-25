package com.example.people.di

import com.example.common.di.holder.FeatureComponentHolder
import com.example.people.di.deps.PeopleDepsStore

object PeopleComponentHolder : FeatureComponentHolder<PeopleComponent>() {
    override fun build(): PeopleComponent =
        DaggerPeopleComponent.builder()
            .dependencies(PeopleDepsStore.deps)
            .build()

}

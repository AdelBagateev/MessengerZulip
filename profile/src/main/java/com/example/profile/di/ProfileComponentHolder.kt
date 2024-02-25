package com.example.profile.di

import com.example.common.di.holder.FeatureComponentHolder
import com.example.profile.di.deps.ProfileDepsStore

object ProfileComponentHolder : FeatureComponentHolder<ProfileComponent>() {
    override fun build(): ProfileComponent =
        DaggerProfileComponent.builder()
            .dependencies(ProfileDepsStore.deps)
            .build()
}

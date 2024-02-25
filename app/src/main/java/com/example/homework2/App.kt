package com.example.homework2

import android.app.Application
import com.example.channels.di.deps.ChannelDepsStore
import com.example.homework2.di.AppComponentHolder
import com.example.messenger.di.deps.MessengerDepsStore
import com.example.people.di.deps.PeopleDepsStore
import com.example.profile.di.deps.ProfileDepsStore
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        AppComponentHolder.build(applicationContext)

        ProfileDepsStore.deps = AppComponentHolder.get()
        MessengerDepsStore.deps = AppComponentHolder.get()
        ChannelDepsStore.deps = AppComponentHolder.get()
        PeopleDepsStore.deps = AppComponentHolder.get()
    }
}

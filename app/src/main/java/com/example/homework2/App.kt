package com.example.homework2

import android.app.Application
import android.content.Context
import com.example.homework2.global_di.AppComponent
import com.example.homework2.global_di.DaggerAppComponent
import timber.log.Timber

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appContext(this)
            .build()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
fun Context.getAppComponent(): AppComponent = (this.applicationContext as App).appComponent

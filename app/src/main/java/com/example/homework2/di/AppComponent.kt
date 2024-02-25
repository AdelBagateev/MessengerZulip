package com.example.homework2.di

import android.content.Context
import com.example.channels.di.deps.ChannelDeps
import com.example.common.di.holder.DiComponent
import com.example.homework2.MainActivity
import com.example.homework2.MainFragment
import com.example.homework2.di.bind.BindModule
import com.example.homework2.di.db.AppDatabaseModule
import com.example.homework2.di.navigation.AppNavigationModule
import com.example.homework2.di.network.AppNetworkModule
import com.example.messenger.di.deps.MessengerDeps
import com.example.people.di.deps.PeopleDeps
import com.example.profile.di.deps.ProfileDeps
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : ProfileDeps, MessengerDeps, ChannelDeps, PeopleDeps, DiComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(applicationContext: Context): Builder
        fun build(): AppComponent
    }
}

@Module(
    includes = [AppNavigationModule::class, AppNetworkModule::class,
        AppDatabaseModule::class, BindModule::class]
)
class AppModule

package com.example.homework2.global_di

import android.content.Context
import com.example.homework2.MainActivity
import com.example.homework2.MainFragment
import com.example.homework2.channels.data.stream.datasource.local.StreamDao
import com.example.homework2.channels.data.stream.datasource.remote.StreamApi
import com.example.homework2.channels.data.topic.datasource.local.TopicDao
import com.example.homework2.channels.data.topic.datasource.remote.TopicApi
import com.example.homework2.channels.presentation.elm.ChannelStateManager
import com.example.homework2.dialog.data.datasource.local.MessageDao
import com.example.homework2.dialog.data.datasource.remote.DialogApi
import com.example.homework2.dialog.presentation.elm.DialogStateManager
import com.example.homework2.global_di.apis.CoreApiModule
import com.example.homework2.global_di.db.AppDatabaseModule
import com.example.homework2.global_di.navigation.AppNavigationModule
import com.example.homework2.global_di.network.AppNetworkModule
import com.example.homework2.people.data.datasource.local.PeopleDao
import com.example.homework2.people.data.datasource.remote.PeopleApi
import com.example.homework2.people.presentation.elm.PeopleStateManager
import com.example.homework2.profile.data.datasource.local.UserDao
import com.example.homework2.profile.data.datasource.remote.ProfileApi
import com.example.homework2.profile.presentation.elm.ProfileStateManager
import com.github.terrakok.cicerone.Router
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)

    fun userDao(): UserDao
    fun peopleDao(): PeopleDao
    fun streamDao(): StreamDao
    fun topicDao() : TopicDao
    fun messageDao() : MessageDao

    fun profileApi(): ProfileApi
    fun peopleApi(): PeopleApi
    fun dialogApi(): DialogApi
    fun streamApi(): StreamApi
    fun topicApi(): TopicApi

    fun profileStateManager(): ProfileStateManager
    fun peopleStateManager(): PeopleStateManager
    fun dialogStateManager(): DialogStateManager
    fun channelStateManager(): ChannelStateManager

    fun router(): Router

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(applicationContext: Context): Builder
        fun build(): AppComponent
    }
}

@Module(
    includes = [AppNavigationModule::class, AppNetworkModule::class,
        AppDatabaseModule::class, CoreApiModule::class]
)
class AppModule

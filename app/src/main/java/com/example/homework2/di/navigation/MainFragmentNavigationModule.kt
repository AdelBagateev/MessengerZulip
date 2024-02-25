package com.example.homework2.di.navigation

import com.example.homework2.di.qualifiers.MainFragmentCicerone
import com.example.homework2.di.qualifiers.MainFragmentNavigatorHolder
import com.example.homework2.di.qualifiers.MainFragmentRouter
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainFragmentNavigationModule {
    @Singleton
    @Provides
    @MainFragmentCicerone
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @MainFragmentRouter
    fun provideRouter(@MainFragmentCicerone cicerone: Cicerone<Router>): Router = cicerone.router

    @Provides
    @MainFragmentNavigatorHolder
    fun provideNavigatorHolder(@MainFragmentCicerone cicerone: Cicerone<Router>): NavigatorHolder =
        cicerone.getNavigatorHolder()
}

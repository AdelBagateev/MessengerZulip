package com.example.homework2.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainFragmentCicerone

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainFragmentRouter

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainFragmentNavigatorHolder

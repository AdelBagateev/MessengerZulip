package com.example.homework2.dialog.di

import androidx.lifecycle.Lifecycle
import com.example.homework2.dialog.presentation.ui.MessengerFragment
import com.example.homework2.global_di.AppComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module

@DialogScope
@Component(dependencies = [AppComponent::class], modules = [DialogModule::class])
interface DialogComponent {
    fun inject(messengerFragment: MessengerFragment)

    fun lifecycle(lifecycle: Lifecycle): Lifecycle

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun lifecycle(lifecycle: Lifecycle): Builder
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): DialogComponent
    }
}

@Module(includes = [DialogBindModule::class, DialogStoreHolderModule::class])
class DialogModule

package com.example.messenger.di


import com.example.common.di.holder.DiComponent
import com.example.messenger.di.bind.MessengerBindModule
import com.example.messenger.di.data.MessengerApiModule
import com.example.messenger.di.data.MessengerDaoModule
import com.example.messenger.di.deps.MessengerDeps
import com.example.messenger.di.store.MessengerStoreHolderModule
import com.example.messenger.di.util.MessengerUtilModule
import com.example.messenger.presentation.ui.MessengerFragment
import dagger.Component
import dagger.Module

@MessengerScope
@Component(dependencies = [MessengerDeps::class], modules = [MessengerModule::class])
interface MessengerComponent : DiComponent {
    fun inject(messengerFragment: MessengerFragment)

    @Component.Builder
    interface Builder {
        fun dependencies(messengerDeps: MessengerDeps): Builder
        fun build(): MessengerComponent
    }
}


@Module(
    includes = [MessengerBindModule::class, MessengerStoreHolderModule::class,
        MessengerApiModule::class, MessengerDaoModule::class, MessengerUtilModule::class]
)
class MessengerModule

package com.example.homework2

import com.example.common.adapter.DelegateAdapterItemCallback
import com.example.common.adapter.MainAdapter
import com.example.common.adapter.interfaces.AdapterDelegate
import com.example.common.adapter.interfaces.DelegateItem
import com.example.homework2.di.AppComponent
import com.example.homework2.di.navigation.AppNavigationModule
import com.example.homework2.di.navigation.MainFragmentNavigationModule
import com.example.homework2.di.network.AppNetworkModule
import com.example.homework2.di.network.AppNetworkUtilsModule
import com.example.messenger.di.MessengerComponent
import com.example.messenger.di.data.MessengerDaoModule
import com.example.messenger.presentation.elm.MessengerActor
import com.example.messenger.presentation.elm.MessengerReducer
import com.example.messenger.presentation.ui.MessengerFragment
import com.example.messenger.presentation.ui.decorators.StickyHeaderItemDecoration
import com.example.messenger.presentation.ui.views.EmojiView
import com.example.messenger.presentation.ui.views.FlexBoxLayout
import com.example.messenger.presentation.ui.views.MessageWithEmojisLayout
import com.example.people.presentation.elm.PeopleActor

class TechnologyInUse {
    //кастомная View для отображения Emoji реакции и кол-ва реакций
    val v1 = EmojiView::class

    //кастомный ViewGroup, для расположения Emoji в строчку с переносом
    val v2 = FlexBoxLayout::class

    //кастомный ViewGroup, которая является как раз таки сообщением + item'ом для RecyclerView
    val v3 = MessageWithEmojisLayout::class

    //Адаптер на делегатах
    val v4 = MainAdapter::class
    val v5 = AdapterDelegate::class
    val v6 = DelegateItem::class
    val v7 = DelegateAdapterItemCallback::class

    //декоратор для stickyHeader даты в спике чатов
    val v8 = StickyHeaderItemDecoration::class

    //навигация на базе cicerone
    val v9 = AppNavigationModule::class
    val v10 = MainFragmentNavigationModule::class

    //"умная" фильтрация списка с пмощью флоу и всех его приколюх
    val v11 = PeopleActor::class //метод filterPeopleList()

    //вся ассинхронщина - flow+coroutine разбросаны по всему проекту
    //особо сконцентрированы в Actor'ах фич

    //состояние ожидания данных в основном реализованны через shimmer'ы(использовал библу от фейсбука)

    //архитектура presentation слоя - MVI(в реализации от vivid money - elmslie)
    val v12 = MessengerActor::class
    val v13 = MessengerReducer::class
    val v14 = MessengerFragment::class

    //DI - Dagger 2, без сабкомпонетов, только через dependencies
    val v15 = AppComponent::class
    val v16 = MessengerComponent::class

    //сеть - Retrofit, api - zulip api, json парсер - kotlinSerialization
    val v123 = AppNetworkModule::class
    val v124 = AppNetworkUtilsModule::class
    //БД - Room, это единственный источник правды для presenter, все данные оборачиваю во Flow<>
    //и подписываюсь на обновления
    val v17 = AppDatabase::class
    val v18 = MessengerDaoModule::class

    //кастомная пагинация
    val v19 = MessengerFragment::class // inner class MessageRvScrollListener

    //отправка медиа через photoPicker
    val v20 = MessengerFragment::class //метод initPhotoPicker()

    //Unit тесты - не получается ссылку оставить(( Укажу расположеение ниже
    //модуль Messenger, директория тестов, MessengerActorTest.kt

    //UI тесты - не получается ссылку оставить(( Укажу расположеение ниже
    //модуль App, директория android-тестов, MessengerTest.kt
}

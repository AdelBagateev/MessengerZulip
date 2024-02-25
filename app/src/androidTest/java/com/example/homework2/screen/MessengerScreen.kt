package com.example.homework2.screen

import android.view.View
import com.example.messenger.R
import com.example.messenger.presentation.ui.MessengerFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KSnackbar
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object MessengerScreen : KScreen<MessengerScreen>() {
    override val layoutId: Int
        get() = R.layout.fragment_messenger
    override val viewClass: Class<*>
        get() = MessengerFragment::class.java

    val messageList = KRecyclerView({ withId(R.id.rv_messages) }, { itemType { DialogItem(it) } })

    class DialogItem(val parent: Matcher<View>) : KRecyclerItem<DialogItem>(parent) {
        val date = KTextView(parent) { withId(R.id.tv_date) }
        val message = KTextView(parent) { withId(R.id.message) }
        val reactions = KView(parent) { withId(R.id.reactions) }
    }

    fun getMessageListSize(): Int {
        return messageList.getSize()
    }

    val errorSnackbar = KSnackbar()
}

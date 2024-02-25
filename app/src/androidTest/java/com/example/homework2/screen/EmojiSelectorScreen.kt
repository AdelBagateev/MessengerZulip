package com.example.homework2.screen

import android.view.View
import com.example.messenger.R
import com.example.messenger.presentation.ui.dialogs.EmojiBottomSheetDialogFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import org.hamcrest.Matcher

object EmojiSelectorScreen : KScreen<EmojiSelectorScreen>() {
    override val layoutId: Int
        get() = R.layout.fragment_bottom_sheet_dialog_emoji
    override val viewClass: Class<*>
        get() = EmojiBottomSheetDialogFragment::class.java
    val emojiList = KRecyclerView({ withId(R.id.rv_emojis) }, { itemType { EmojiItem(it) } })

    class EmojiItem(parent: Matcher<View>) : KRecyclerItem<EmojiItem>(parent) {
        val emoji = KView(parent) { isFirst() }
    }
}

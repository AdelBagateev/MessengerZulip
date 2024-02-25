package com.example.homework2.screen

import com.example.messenger.R
import com.example.messenger.presentation.ui.dialogs.ActionChooserBottomSheetDialogFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.text.KButton

object ActionChooserScreen : KScreen<ActionChooserScreen>() {
    override val layoutId: Int
        get() = R.layout.fragment_bottom_sheet_dialog_action_chooser
    override val viewClass: Class<*>
        get() = ActionChooserBottomSheetDialogFragment::class.java

    val addReactionBtn = KButton { withId(R.id.add_reaction) }
}

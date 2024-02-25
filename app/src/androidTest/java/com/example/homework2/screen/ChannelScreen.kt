package com.example.homework2.screen

import android.view.View
import com.example.channels.R
import com.example.channels.presentation.ui.StreamsFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object ChannelScreen : KScreen<ChannelScreen>() {
    override val layoutId: Int
        get() = R.layout.fragment_chanels
    override val viewClass: Class<*>
        get() = StreamsFragment::class.java

    val streamList = KRecyclerView({ withId(R.id.rv_streams) }, { itemType { StreamItem(it) } })

    class StreamItem(parent: Matcher<View>) : KRecyclerItem<StreamItem>(parent) {
        val topicName = KTextView(parent) { withId(R.id.topic_name) }
        val btnShowTopics = KImageView(parent) { withId(R.id.btn_arrow) }
    }
}

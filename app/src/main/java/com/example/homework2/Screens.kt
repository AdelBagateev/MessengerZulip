package com.example.homework2

import com.example.homework2.channels.domain.model.TopicModel
import com.example.homework2.channels.presentation.ui.ChannelFragment
import com.example.homework2.dialog.presentation.ui.MessengerFragment
import com.example.homework2.people.presentation.ui.PeopleFragment
import com.example.homework2.profile.presentation.ui.ProfileFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun Dialog(model: TopicModel) = FragmentScreen { MessengerFragment.newInstance(model) }
    fun Chanel() = FragmentScreen { ChannelFragment() }
    fun People() = FragmentScreen { PeopleFragment() }
    fun Profile(userId: Int, fromPeopleFragment: Boolean = false) =
        FragmentScreen { ProfileFragment.newInstance(userId, fromPeopleFragment) }
    fun Main() = FragmentScreen { MainFragment() }
}

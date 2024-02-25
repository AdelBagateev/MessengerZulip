package com.example.homework2

import com.example.channels.presentation.ui.ChannelFragment
import com.example.messenger.presentation.ui.MessengerFragment
import com.example.people.presentation.ui.PeopleFragment
import com.example.profile.presentation.ui.ProfileFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun Messenger(streamName: String, topicName: String?) =
        FragmentScreen { MessengerFragment.newInstance(streamName, topicName) }

    fun Chanel() = FragmentScreen { ChannelFragment() }
    fun People() = FragmentScreen { PeopleFragment() }
    fun Profile(userId: Int, fromPeopleFragment: Boolean = false) =
        FragmentScreen { ProfileFragment.newInstance(userId, fromPeopleFragment) }

    fun Main() = FragmentScreen { MainFragment() }
}

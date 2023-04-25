package com.example.homework2.people.utils

import com.example.homework2.people.domain.model.PeopleModel
import com.example.homework2.people.domain.model.PresenceModel
import com.example.homework2.people.presentation.ui.delegate.PeopleDelegateItem
import javax.inject.Inject

interface PeopleMapperPresentation {
    fun toPeopleDelegateItem(
        peopleModels: List<PeopleModel>,
    ): List<PeopleDelegateItem>
}

class PeopleMapperPresentationImpl @Inject constructor() : PeopleMapperPresentation {
    override fun toPeopleDelegateItem(
        peopleModels: List<PeopleModel>,
    ): List<PeopleDelegateItem> {
       return  peopleModels.map { PeopleDelegateItem(it) }.
               sortedBy {
                   when(it.value.status) {
                       PresenceModel.ONLINE_STATUS -> 0
                       PresenceModel.IDLE_STATUS -> 1
                       else -> 2
                   }
               }
    }
}

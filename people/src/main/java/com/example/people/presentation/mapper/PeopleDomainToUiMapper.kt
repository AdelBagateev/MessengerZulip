package com.example.people.presentation.mapper

import com.example.people.domain.model.PeopleModel
import com.example.people.domain.model.PresenceModel
import com.example.people.presentation.ui.delegate.PeopleDelegateItem
import javax.inject.Inject

interface PeopleDomainToUiMapper {
    fun toDelegateItem(
        peopleModels: List<PeopleModel>,
    ): List<PeopleDelegateItem>
}

internal class PeopleDomainToUiMapperImpl @Inject constructor() :
    PeopleDomainToUiMapper {
    override fun toDelegateItem(
        peopleModels: List<PeopleModel>,
    ): List<PeopleDelegateItem> {
        return peopleModels.map { PeopleDelegateItem(it) }.sortedBy {
            when (it.value.status) {
                PresenceModel.ONLINE_STATUS -> 0
                PresenceModel.IDLE_STATUS -> 1
                else -> 2
            }
        }
    }
}

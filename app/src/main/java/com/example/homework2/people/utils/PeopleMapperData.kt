package com.example.homework2.people.utils

import com.example.homework2.people.data.datasource.local.entity.PeopleDB
import com.example.homework2.people.data.datasource.remote.response.PeoplePresenceResponse
import com.example.homework2.people.data.datasource.remote.response.PeopleResponse
import com.example.homework2.people.domain.model.PeopleModel
import com.example.homework2.people.domain.model.PresenceModel
import javax.inject.Inject

interface PeopleMapperData {
    fun toPeopleDB(
        presences: PeoplePresenceResponse,
        people: PeopleResponse
    ): List<PeopleDB>

    fun toPeopleModel(peopleDB: List<PeopleDB>) : List<PeopleModel>
}

class PeopleMapperDataImpl @Inject constructor() : PeopleMapperData {
    override fun toPeopleDB(
        presences: PeoplePresenceResponse,
        people: PeopleResponse
    ): List<PeopleDB> {
        val list = people.members
        return list.map {
            PeopleDB(
                id = it.user_id,
                fullName = it.full_name,
                avatarUrl = it.avatar_url,
                mail = it.email,
                status = getStatus(presences, it.email),
                isBot = it.is_bot
            )
        }
    }

    override fun toPeopleModel(peopleDB: List<PeopleDB>): List<PeopleModel> {
       return  peopleDB.map { it.toPeopleModel()}
    }
    private fun getStatus(
        presences: PeoplePresenceResponse,
        email: String
    ): String {
        val map = presences.presences
        val serverTimestamp = presences.server_timestamp

        val activities = map[email] ?: return PresenceModel.OFFLINE_STATUS
        val lastActivityTimestamp = activities.values.first().timestamp

        return calculateStatus(lastActivityTimestamp, serverTimestamp)
    }

    private fun calculateStatus(timestamp1: Double, timestamp2: Double): String {
        val diffInSeconds = (timestamp2 - timestamp1) / 1000
        val diffInMinutes = diffInSeconds / 60
        return if (diffInMinutes > OFFLINE_TIME) {
            PresenceModel.OFFLINE_STATUS
        } else if (diffInMinutes > IDLE_TIME) {
            PresenceModel.IDLE_STATUS
        } else {
            PresenceModel.ONLINE_STATUS
        }
    }

    private fun PeopleDB.toPeopleModel() : PeopleModel =
        PeopleModel(id, fullName, avatarUrl, mail, status, isBot)

    private companion object {
        const val OFFLINE_TIME = 0.2
        const val IDLE_TIME = 0.0008
    }
}

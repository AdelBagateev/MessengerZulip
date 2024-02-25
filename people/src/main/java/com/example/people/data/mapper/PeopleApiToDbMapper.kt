package com.example.people.data.mapper

import com.example.people.data.datasource.local.entity.PeopleDB
import com.example.people.data.datasource.remote.response.PeoplePresenceResponse
import com.example.people.data.datasource.remote.response.PeopleResponse
import com.example.people.domain.model.PresenceModel
import javax.inject.Inject

interface PeopleApiToDbMapper {
    fun toPeopleDB(
        presences: PeoplePresenceResponse,
        people: PeopleResponse
    ): List<PeopleDB>
}

internal class PeopleApiToDbMapperImpl @Inject constructor() : PeopleApiToDbMapper {
    override fun toPeopleDB(
        presences: PeoplePresenceResponse,
        people: PeopleResponse
    ): List<PeopleDB> {
        val list = people.members
        return list.map {
            PeopleDB(
                id = it.userId,
                fullName = it.fullName,
                avatarUrl = it.avatarUrl,
                mail = it.email,
                status = getStatus(presences, it.email),
                isBot = it.isBot
            )
        }
    }

    private fun getStatus(
        presences: PeoplePresenceResponse,
        email: String
    ): String {
        val map = presences.presences
        val serverTimestamp = presences.serverTimestamp

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

    private companion object {
        const val OFFLINE_TIME = 0.2
        const val IDLE_TIME = 0.0008
    }
}

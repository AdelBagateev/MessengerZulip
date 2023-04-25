package com.example.homework2.people.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people")
class PeopleDB (
    @PrimaryKey
    val id: Int,
    @ColumnInfo("full_name")
    val fullName: String,
    @ColumnInfo("avatar_url")
    val avatarUrl: String?,
    val mail: String,
    val status: String,
    @ColumnInfo("is_bot")
    val isBot: Boolean
)

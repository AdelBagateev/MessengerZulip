package com.example.homework2.profile.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserDB (
    @PrimaryKey
    val id: Int,
    val avatar: String?,
    val name: String,
    val status: String,
)

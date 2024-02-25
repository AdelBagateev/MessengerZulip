package com.example.profile.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserDB(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("avatar")
    val avatar: String?,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("status")
    val status: String,
)

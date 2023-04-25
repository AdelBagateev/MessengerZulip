package com.example.homework2.profile.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homework2.profile.data.datasource.local.entity.UserDB
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE id LIKE :id")
    fun getUserById(id: Int): Flow<UserDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: UserDB)
}


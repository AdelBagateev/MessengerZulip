package com.example.homework2.people.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homework2.people.data.datasource.local.entity.PeopleDB
import kotlinx.coroutines.flow.Flow

@Dao
interface PeopleDao {
    @Query("SELECT * FROM people")
    fun getPeople(): Flow<List<PeopleDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePeople(people: List<PeopleDB>)
}

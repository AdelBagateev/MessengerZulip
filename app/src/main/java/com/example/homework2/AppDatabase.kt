package com.example.homework2

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.channels.data.stream.datasource.local.StreamDao
import com.example.channels.data.stream.datasource.local.entity.StreamDB
import com.example.channels.data.topic.datasource.local.TopicDao
import com.example.channels.data.topic.datasource.local.entity.TopicDB
import com.example.channels.di.deps.ChannelDatabase
import com.example.messenger.data.datasource.local.MessageDao
import com.example.messenger.data.datasource.local.entity.MessageDB
import com.example.messenger.data.datasource.local.entity.ReactionDB
import com.example.messenger.di.deps.MessageDatabase
import com.example.people.data.datasource.local.PeopleDao
import com.example.people.data.datasource.local.entity.PeopleDB
import com.example.people.di.deps.PeopleDatabase
import com.example.profile.data.datasource.local.UserDao
import com.example.profile.data.datasource.local.entity.UserDB
import com.example.profile.di.deps.UserDatabase

@Database(
    entities = [
        UserDB::class, PeopleDB::class, StreamDB::class,
        TopicDB::class, MessageDB::class, ReactionDB::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase(),
    UserDatabase, MessageDatabase, ChannelDatabase, PeopleDatabase {
    abstract override fun userDao(): UserDao
    abstract override fun peopleDao(): PeopleDao
    abstract override fun streamDao(): StreamDao
    abstract override fun topicDao(): TopicDao
    abstract override fun messageDao(): MessageDao

    companion object {
        const val DB_NAME = "AppDataBase"
    }
}

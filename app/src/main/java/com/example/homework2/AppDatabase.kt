package com.example.homework2

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.homework2.channels.data.stream.datasource.local.StreamDao
import com.example.homework2.channels.data.stream.datasource.local.entity.StreamDB
import com.example.homework2.channels.data.topic.datasource.local.TopicDao
import com.example.homework2.channels.data.topic.datasource.local.entity.TopicDB
import com.example.homework2.dialog.data.datasource.local.MessageDao
import com.example.homework2.dialog.data.datasource.local.entity.MessageDB
import com.example.homework2.dialog.data.datasource.local.entity.ReactionDB
import com.example.homework2.people.data.datasource.local.PeopleDao
import com.example.homework2.people.data.datasource.local.entity.PeopleDB
import com.example.homework2.profile.data.datasource.local.UserDao
import com.example.homework2.profile.data.datasource.local.entity.UserDB

@Database(entities = [
    UserDB::class, PeopleDB::class, StreamDB::class,
    TopicDB::class, MessageDB::class, ReactionDB::class
], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun peopleDao() : PeopleDao
    abstract fun streamDao() : StreamDao
    abstract fun topicDao() : TopicDao
    abstract fun messageDao() : MessageDao
}

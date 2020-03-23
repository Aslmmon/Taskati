package com.tasks.taskati.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tasks.taskati.common.data.db.comments_table.CommentsDao
import com.tasks.taskati.common.data.db.comments_table.CommentsTable

@Database(entities = [TaskTable::class, CommentsTable::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TasksDao
    abstract fun commentDao(): CommentsDao
}
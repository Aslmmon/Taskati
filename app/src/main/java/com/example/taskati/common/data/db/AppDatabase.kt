package com.example.taskati.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TasksDao
}
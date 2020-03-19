package com.example.taskati.common.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskTable(
    @PrimaryKey(onConflict = OnConflictStrategy.REPLACE, autoGenerate = true) var id: Int=1,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "done") var isDone: Boolean,
    @ColumnInfo(name = "difficulty") var difficulty: Int
) {
}
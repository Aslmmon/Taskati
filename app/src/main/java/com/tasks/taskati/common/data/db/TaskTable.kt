package com.tasks.taskati.common.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "task_table")
data class TaskTable(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "done") var isDone: Boolean,
    @ColumnInfo(name = "difficulty") var difficulty: Int
):Serializable {
}
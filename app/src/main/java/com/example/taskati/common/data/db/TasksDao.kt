package com.example.taskati.common.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TasksDao {
    @Insert
    suspend fun insert(taks: TaskTable)

    @Query("SELECT * FROM task_table ")
    fun getAllTasks(): List<TaskTable>
}
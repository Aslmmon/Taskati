package com.example.taskati.common.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TasksDao {
    @Insert
    suspend fun insert(taks: TaskTable)

    @Query("SELECT * FROM task_table ")
    suspend fun getAllTasks(): List<TaskTable>

    @Query("DELETE FROM task_table WHERE id = :userId")
    suspend fun deleteTask(userId: Int)

    @Query("UPDATE task_table SET done = :done WHERE id = :userId")
    suspend fun updateDoneTask(userId: Int, done: Boolean)

    @Query("UPDATE task_table SET difficulty = :diffic WHERE id = :userId")
    suspend fun updatePeriorityTask(userId: Int, diffic: Int)

}
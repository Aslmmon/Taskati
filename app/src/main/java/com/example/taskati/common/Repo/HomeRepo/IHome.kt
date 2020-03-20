package com.example.taskati.common.Repo.HomeRepo

import com.example.taskati.common.data.db.TaskTable

interface IHome {

    suspend fun saveToDatabase(taks: TaskTable)
    suspend fun getTasks(): List<TaskTable>
    suspend fun updateDoneTask(id: Int, done: Boolean)
    suspend fun updatePeriorityTask(id: Int, periority: Int)

}
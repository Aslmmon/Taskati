package com.example.taskati.common.Repo.HomeRepo

import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.common.model.UserWithComments

interface IHome {

    suspend fun saveToDatabase(taks: TaskTable)
    suspend fun getTasks(): List<TaskTable>
    suspend fun updateDoneTask(task: TaskTable)

    suspend fun getUsersWithComments():List<UserWithComments>

}
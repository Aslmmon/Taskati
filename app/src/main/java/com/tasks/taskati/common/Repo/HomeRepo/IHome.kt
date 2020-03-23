package com.tasks.taskati.common.Repo.HomeRepo

import com.tasks.taskati.common.data.db.TaskTable
import com.tasks.taskati.common.model.UserWithComments

interface IHome {

    suspend fun saveToDatabase(taks: TaskTable)
    suspend fun getTasks(): List<TaskTable>
    suspend fun updateDoneTask(task: TaskTable)

    suspend fun getUsersWithComments():List<UserWithComments>

}
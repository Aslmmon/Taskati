package com.example.taskati.common.Repo.HomeRepo

import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.common.data.db.TasksDao
import com.example.taskati.common.data.db.comments_table.CommentsDao
import com.example.taskati.common.model.UserWithComments

class HomeRepo(var database: TasksDao,var databaseComments:CommentsDao) : IHome {
    override suspend fun saveToDatabase(task: TaskTable) = database.insert(taks = task)
    override suspend fun getTasks() = database.getAllTasks()
    override suspend fun updateDoneTask(task: TaskTable)  = database.updateDoneTask(task)
    override suspend fun updatePeriorityTask(id: Int, periority: Int) = database.updatePeriorityTask(id,periority)
    override suspend fun getUsersWithComments(): List<UserWithComments>  = databaseComments.getAllComments()
    // override suspend fun getAllTasksWithComments()  = databaseComments.getAllComments()
}
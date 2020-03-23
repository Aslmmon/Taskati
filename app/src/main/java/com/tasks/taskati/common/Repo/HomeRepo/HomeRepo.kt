package com.tasks.taskati.common.Repo.HomeRepo

import com.tasks.taskati.common.data.db.TaskTable
import com.tasks.taskati.common.data.db.TasksDao
import com.tasks.taskati.common.data.db.comments_table.CommentsDao
import com.tasks.taskati.common.model.UserWithComments

class HomeRepo(var database: TasksDao,var databaseComments:CommentsDao) : IHome {
    override suspend fun saveToDatabase(task: TaskTable) = database.insert(taks = task)
    override suspend fun getTasks() = database.getAllTasks()
    override suspend fun updateDoneTask(task: TaskTable)  = database.updateDoneTask(task)
    override suspend fun getUsersWithComments(): List<UserWithComments>  = databaseComments.getAllComments()
}
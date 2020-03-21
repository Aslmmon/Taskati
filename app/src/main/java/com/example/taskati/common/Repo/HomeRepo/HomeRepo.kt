package com.example.taskati.common.Repo.HomeRepo

import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.common.data.db.TasksDao

class HomeRepo(var database: TasksDao) : IHome {
    override suspend fun saveToDatabase(task: TaskTable) = database.insert(taks = task)
    override suspend fun getTasks() = database.getAllTasks()
    override suspend fun updateDoneTask(task: TaskTable)  = database.updateDoneTask(task)
    override suspend fun updatePeriorityTask(id: Int, periority: Int) = database.updatePeriorityTask(id,periority)
}
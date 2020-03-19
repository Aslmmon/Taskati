package com.example.taskati.common.Repo.DetailRepo

import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.common.data.db.TasksDao

class DetailRepo(var database: TasksDao) : IDetail {
    override suspend fun deleteTask(id: Int) = database.deleteTask(id)
}
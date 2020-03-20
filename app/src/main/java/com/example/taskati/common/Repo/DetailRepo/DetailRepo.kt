package com.example.taskati.common.Repo.DetailRepo

import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.common.data.db.TasksDao
import com.example.taskati.common.data.db.comments_table.CommentsDao
import com.example.taskati.common.data.db.comments_table.CommentsTable

class DetailRepo(var database: TasksDao, var databaseComments: CommentsDao) : IDetail {
    override suspend fun deleteTask(task: TaskTable) = database.deleteTask(task)
    override suspend fun saveCommentToTask(commentsTable: CommentsTable) =
        databaseComments.saveComments(commentsTable)

    override suspend fun getComments(): List<CommentsTable> = databaseComments.getAllComments()
}
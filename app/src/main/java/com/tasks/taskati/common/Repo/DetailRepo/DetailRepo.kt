package com.tasks.taskati.common.Repo.DetailRepo

import com.tasks.taskati.common.data.db.TaskTable
import com.tasks.taskati.common.data.db.TasksDao
import com.tasks.taskati.common.data.db.comments_table.CommentsDao
import com.tasks.taskati.common.data.db.comments_table.CommentsTable
import com.tasks.taskati.common.model.UserWithComments

class DetailRepo(var database: TasksDao, var databaseComments: CommentsDao) : IDetail {
    override suspend fun deleteTask(task: TaskTable) = database.deleteTask(task)
    override suspend fun saveCommentToTask(commentsTable: CommentsTable) = databaseComments.saveComments(commentsTable)
    override suspend fun getUserWithComments(userId:Int): List<UserWithComments>  = databaseComments.getUserWithComments(userId)
}
package com.tasks.taskati.common.Repo.DetailRepo

import com.tasks.taskati.common.data.db.TaskTable
import com.tasks.taskati.common.data.db.comments_table.CommentsTable
import com.tasks.taskati.common.model.UserWithComments

interface IDetail {
    suspend fun deleteTask(task: TaskTable)
    suspend fun saveCommentToTask(commentsTable: CommentsTable)
    suspend fun getUserWithComments(userId:Int): List<UserWithComments>

}
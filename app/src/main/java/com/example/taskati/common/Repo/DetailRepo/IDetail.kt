package com.example.taskati.common.Repo.DetailRepo

import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.common.data.db.comments_table.CommentsTable

interface IDetail {
    suspend fun deleteTask(task: TaskTable)
    suspend fun saveCommentToTask(commentsTable: CommentsTable)
    suspend fun getComments(): List<CommentsTable>
}
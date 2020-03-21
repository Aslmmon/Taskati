package com.example.taskati.common.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.taskati.common.data.db.TaskTable
import com.example.taskati.common.data.db.comments_table.CommentsTable

data class UserWithComments(
    @Embedded val taskTable: TaskTable,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_user"
    )
    val comments: List<CommentsTable>
)
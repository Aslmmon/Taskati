package com.example.taskati.common.data.db.comments_table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "comments_table")
data class CommentsTable(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "id_user") var uid: Int,
    @ColumnInfo(name = "comments") var comments: String
) {
}
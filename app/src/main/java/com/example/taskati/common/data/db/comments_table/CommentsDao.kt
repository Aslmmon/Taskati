package com.example.taskati.common.data.db.comments_table

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.taskati.common.model.UserWithComments

@Dao
interface CommentsDao {
    @Insert
    suspend fun saveComments(comments: CommentsTable)

    @Query("SELECT * FROM comments_table")
    suspend fun getAllComments(): List<CommentsTable>

    @Transaction
    @Query("SELECT * FROM task_table WHERE id = :userId")
    suspend fun getUserWithComments(userId:Int):List<UserWithComments>


//    @Query("DELETE FROM task_table WHERE id = :userId")
//    suspend fun deleteTask(userId: Int)


}
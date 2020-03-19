package com.example.taskati.common.data.db.comments_table

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CommentsDao {
    @Insert
    suspend fun saveComments(comments: CommentsTable)

    @Query("SELECT * FROM comments_table WHERE id_user = :userID")
    suspend fun getAllComments(userID:Int): List<CommentsTable>


//    @Query("DELETE FROM task_table WHERE id = :userId")
//    suspend fun deleteTask(userId: Int)


}
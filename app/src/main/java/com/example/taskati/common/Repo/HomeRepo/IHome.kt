package com.example.taskati.common.Repo.HomeRepo

import androidx.lifecycle.LiveData
import com.example.taskati.common.data.db.TaskTable

interface IHome {

    suspend fun saveToDatabase(taks: TaskTable)
    suspend fun getTasks(): List<TaskTable>
}
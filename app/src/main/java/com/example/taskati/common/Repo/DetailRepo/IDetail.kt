package com.example.taskati.common.Repo.DetailRepo

interface IDetail {
    suspend fun deleteTask(id:Int)
}
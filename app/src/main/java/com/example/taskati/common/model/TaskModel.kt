package com.example.taskati.common.model

data class TaskModel(
    var title: String,
    var date: String,
    var done: Boolean,
    var difficultyIndicator: Int
)
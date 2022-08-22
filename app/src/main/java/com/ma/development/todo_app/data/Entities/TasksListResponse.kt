package com.ma.development.todo_app.data.Entities

data class TasksListResponse(
    val data: List<TaskData>, val count: Int
)
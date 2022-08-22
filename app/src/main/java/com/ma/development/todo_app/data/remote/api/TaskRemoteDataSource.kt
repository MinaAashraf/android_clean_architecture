package com.ma.development.todo_app.data.remote.api

import com.ma.development.todo_app.data.Entities.*
import com.ma.development.todo_app.domain.util.Result
import com.ma.development.todo_app.domain.model.Task

interface TaskRemoteDataSource {
    suspend fun getTasks(limit: Int, skip: Int): Result<List<Task>>
    suspend fun getTask(id: String): Result<Task>
    suspend fun addTask(description: TaskDescription): Result<Task>
    suspend fun updateTask(status: TaskStatus, id: String): Result<String>
    suspend fun deleteTask(id: String): Result<String>
}
package com.ma.development.todo_app.data.repository

import androidx.lifecycle.LiveData
import com.ma.development.todo_app.data.Entities.TaskDescription
import com.ma.development.todo_app.domain.util.Result
import com.ma.development.todo_app.domain.model.Task

interface TaskRepository {

    fun getAllTasks(): LiveData<List<Task>>

    fun getCompletedTasks(): LiveData<List<Task>>

    fun getInCompleteTasks(): LiveData<List<Task>>

    fun getTask(id: String): LiveData<Task>

    suspend fun updateTask(isCompleted: Boolean, id: String): Result<String>

    suspend fun deleteTask(task: Task): Result<String>

    suspend fun addTask(description: TaskDescription) : Result<Task>

    suspend fun refreshLocalData(limit:Int,skip:Int) : Result<Int>

}
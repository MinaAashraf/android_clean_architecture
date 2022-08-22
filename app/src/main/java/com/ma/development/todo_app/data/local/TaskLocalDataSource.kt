package com.ma.development.todo_app.data.local

import androidx.lifecycle.LiveData
import com.ma.development.todo_app.domain.model.Task

interface TaskLocalDataSource {

    fun getAllTasks(): LiveData<List<Task>>
    fun getCompletedTasks(): LiveData<List<Task>>
    fun getInCompletedTasks(): LiveData<List<Task>>
    fun getTask(id: String): LiveData<Task>
    suspend fun addTask(task: Task)
    suspend fun addTasks(tasks: List<Task>)
    suspend fun updateTask(isCompleted: Boolean, id: String)
    suspend fun deleteTask(task: Task)
    suspend fun getCount() : Int


}
package com.ma.development.todo_app.data.local

import androidx.lifecycle.LiveData
import com.ma.development.todo_app.data.Entities.TaskData
import com.ma.development.todo_app.data.local.taskdb.TaskDao
import com.ma.development.todo_app.domain.model.Task
import javax.inject.Inject

class TaskLocalDataSourceImpl @Inject constructor(
    private val taskDao: TaskDao,
    ) :
    TaskLocalDataSource {

    override fun getAllTasks(): LiveData<List<Task>> = taskDao.getAllTasks()
    override fun getCompletedTasks(): LiveData<List<Task>> = taskDao.getCompletedTasks()

    override fun getInCompletedTasks(): LiveData<List<Task>> = taskDao.getInCompleteTasks()

    override fun getTask(id: String): LiveData<Task> = taskDao.getTask(id)

    override suspend fun addTask(taskData: Task) {
        taskDao.addTask(taskData)
    }

    override suspend fun addTasks(tasks: List<Task>) {
        taskDao.addTasks(tasks)
    }

    override suspend fun updateTask(isCompleted: Boolean, id: String) {
        taskDao.updateTask(isCompleted, id)
    }

    override suspend fun deleteTask(taskData: Task) {
        taskDao.deleteTask(taskData)
    }

    override suspend fun getCount() = taskDao.getCount()
}
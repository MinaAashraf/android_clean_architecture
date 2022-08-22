package com.ma.development.todo_app.data.local.taskdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ma.development.todo_app.data.Entities.TaskData
import com.ma.development.todo_app.domain.model.Task


@Dao
interface TaskDao {

    @Query("select * from task_table")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("select * from task_table where completed = ${true}")
    fun getCompletedTasks(): LiveData<List<Task>>

    @Query("select * from task_table where completed = ${false}")
    fun getInCompleteTasks(): LiveData<List<Task>>

    @Query("select * from task_table where id = :id")
    fun getTask(id: String): LiveData<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTasks(tasks: List<Task>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(taskData: Task)

    @Query("update task_table set completed = :isCompleted where id = :id")
    suspend fun updateTask(isCompleted: Boolean, id: String)

    @Delete
    suspend fun deleteTask(taskData: Task)

    @Query("select count(id) from task_table")
    suspend fun getCount(): Int




}
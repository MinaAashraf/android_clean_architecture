package com.ma.development.todo_app.data.local.taskdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ma.development.todo_app.data.Entities.TaskData
import com.ma.development.todo_app.domain.model.Task

@Database(entities = [Task::class], version = 2, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getDao(): TaskDao
}
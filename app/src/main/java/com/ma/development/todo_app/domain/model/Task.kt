package com.ma.development.todo_app.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    val completed: Boolean,
    @PrimaryKey
    val id: String,
    val description: String,
    val createdAt: String,
    val updatedAt: String
)



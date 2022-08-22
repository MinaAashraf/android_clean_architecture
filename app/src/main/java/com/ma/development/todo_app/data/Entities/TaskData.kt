package com.ma.development.todo_app.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class TaskData(
    val completed: Boolean?,
    @SerializedName("_id") val id: String?,
    val description: String?,
    val createdAt: String?,
    val updatedAt: String,
    val owner: String,
)

fun TaskData.hasNotNullData(): Boolean {
    return completed != null && id != null && description != null && createdAt != null && completed != null
}
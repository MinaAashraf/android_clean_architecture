package com.ma.development.todo_app.presentation.tasksfeed

import androidx.recyclerview.widget.DiffUtil
import com.ma.development.todo_app.domain.model.Task

class TaskDiffCallBack : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
}
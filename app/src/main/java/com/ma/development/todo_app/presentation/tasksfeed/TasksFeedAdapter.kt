package com.ma.development.todo_app.presentation.tasksfeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ma.development.todo_app.databinding.ItemTaskBinding
import com.ma.development.todo_app.domain.model.Task

class TasksFeedAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Task, TasksFeedAdapter.MyViewHolder>(TaskDiffCallBack()) {

    class MyViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(task: Task) {
            binding.task = task
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        val holder = MyViewHolder(binding)
        binding.root.setOnClickListener { onClickListener.onItemClick(getItem(holder.adapterPosition).id) }
        binding.removeIcon.setOnClickListener { onClickListener.onRemoveIconClick(getItem(holder.adapterPosition)) }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    interface OnClickListener {
        fun onItemClick(taskId: String)
        fun onRemoveIconClick(task: Task)
    }
}
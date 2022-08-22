package com.ma.development.todo_app.domain.usecases

import androidx.lifecycle.LiveData
import com.ma.development.todo_app.data.repository.TaskRepository
import com.ma.development.todo_app.domain.model.Task
import javax.inject.Inject

class GetCompletedTasksUseCase @Inject constructor(private val taskRepository: TaskRepository){

    fun execute () : LiveData<List<Task>> = taskRepository.getCompletedTasks()

}
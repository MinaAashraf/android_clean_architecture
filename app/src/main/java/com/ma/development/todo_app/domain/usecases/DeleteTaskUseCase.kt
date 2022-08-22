package com.ma.development.todo_app.domain.usecases

import com.ma.development.todo_app.data.repository.TaskRepository
import com.ma.development.todo_app.domain.model.Task
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    suspend fun execute (task: Task) = taskRepository.deleteTask(task)

}
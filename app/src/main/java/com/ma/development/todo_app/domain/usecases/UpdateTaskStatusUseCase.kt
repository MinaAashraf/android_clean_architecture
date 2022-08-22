package com.ma.development.todo_app.domain.usecases

import com.ma.development.todo_app.data.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskStatusUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    suspend fun execute(isCompleted: Boolean, taskId: String) =
        taskRepository.updateTask(isCompleted,taskId)

}
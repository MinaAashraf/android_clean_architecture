package com.ma.development.todo_app.domain.usecases

import com.ma.development.todo_app.data.Entities.TaskDescription
import com.ma.development.todo_app.data.repository.TaskRepository
import com.ma.development.todo_app.domain.model.Task
import com.ma.development.todo_app.domain.util.Result
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

   suspend fun execute (description: String): Result<Task> = taskRepository.addTask(TaskDescription(description))

}

package com.ma.development.todo_app.domain.usecases

import com.ma.development.todo_app.data.repository.TaskRepository
import com.ma.development.todo_app.domain.utils.Result
import javax.inject.Inject

class RefreshTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    suspend fun execute(limit: Int, skip: Int): Result<Int> =
        taskRepository.refreshLocalData(limit, skip)

}
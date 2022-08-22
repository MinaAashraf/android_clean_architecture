package com.ma.development.todo_app.data.mapper

import com.ma.development.todo_app.data.Entities.TaskData
import com.ma.development.todo_app.domain.mapper.IMapper
import com.ma.development.todo_app.domain.model.Task

class DataToDomainMapper : IMapper<TaskData, Task> {
    override fun mapTo(input: TaskData): Task =

        Task(
            id = input.id!!,
            description = input.description!!,
            createdAt = input.createdAt!!,
            updatedAt = input.updatedAt,
            completed = input.completed!!
        )

}
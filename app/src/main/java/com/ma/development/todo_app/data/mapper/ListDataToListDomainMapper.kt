package com.ma.development.todo_app.data.mapper

import com.ma.development.todo_app.data.Entities.TaskData
import com.ma.development.todo_app.domain.mapper.IMapper
import com.ma.development.todo_app.domain.model.Task

class ListDataToListDomainMapper(private val dataToDomainMapper: IMapper<TaskData,Task>) :
    IMapper<List<TaskData>, List<Task>> {

    override fun mapTo(input: List<TaskData>): List<Task> =
        input.map { dataToDomainMapper.mapTo(it) }

}
package com.ma.development.todo_app.domain.mapper

interface IMapper<F, T> {

    fun mapTo(input: F): T

}
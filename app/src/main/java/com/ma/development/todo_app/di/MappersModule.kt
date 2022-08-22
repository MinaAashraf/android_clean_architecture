package com.ma.development.todo_app.di

import com.ma.development.todo_app.data.Entities.TaskData
import com.ma.development.todo_app.data.local.TaskLocalDataSource
import com.ma.development.todo_app.data.local.TaskLocalDataSourceImpl
import com.ma.development.todo_app.data.local.taskdb.TaskDao
import com.ma.development.todo_app.data.mapper.DataToDomainMapper
import com.ma.development.todo_app.data.mapper.ListDataToListDomainMapper
import com.ma.development.todo_app.data.remote.api.TaskApi
import com.ma.development.todo_app.data.remote.api.TaskRemoteDataSource
import com.ma.development.todo_app.data.remote.api.TaskRemoteDataSourceImpl
import com.ma.development.todo_app.domain.mapper.IMapper
import com.ma.development.todo_app.domain.model.Task
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MappersModule {

    @Singleton
    @Provides
    fun provideDataToDomainMapper(): IMapper<TaskData, Task> =
        DataToDomainMapper()

    @Singleton
    @Provides
    fun provideListDataToListDomainMapper(dataToDomainMapper: IMapper<TaskData, Task>): IMapper<List<TaskData>, List<Task>> =
        ListDataToListDomainMapper(dataToDomainMapper)

}
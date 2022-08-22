package com.ma.development.todo_app.di

import com.ma.development.todo_app.data.Entities.TaskData
import com.ma.development.todo_app.data.local.TaskLocalDataSource
import com.ma.development.todo_app.data.local.TaskLocalDataSourceImpl
import com.ma.development.todo_app.data.local.taskdb.TaskDao
import com.ma.development.todo_app.data.remote.api.TaskApi
import com.ma.development.todo_app.data.remote.api.TaskRemoteDataSource
import com.ma.development.todo_app.data.remote.api.TaskRemoteDataSourceImpl
import com.ma.development.todo_app.data.repository.TaskRepository
import com.ma.development.todo_app.data.repository.TaskRepositoryImpl
import com.ma.development.todo_app.domain.mapper.IMapper
import com.ma.development.todo_app.domain.model.Task
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideTaskLocalDataSource(taskDao: TaskDao): TaskLocalDataSource =
        TaskLocalDataSourceImpl(taskDao)

    @Singleton
    @Provides
    fun provideTaskRemoteDataSource(
        taskApi: TaskApi, listDataToListDomainMapper: IMapper<List<TaskData>, List<Task>>,
        dataToDomainMapper: IMapper<TaskData, Task>
    ): TaskRemoteDataSource =
        TaskRemoteDataSourceImpl(
            taskApi = taskApi,
            listDataToListDomainMapper = listDataToListDomainMapper,
            dataToDomainMapper = dataToDomainMapper
        )

    @Singleton
    @Provides
    fun provideTaskRepository(
        remoteDataSource: TaskRemoteDataSource, localDataSource: TaskLocalDataSource,
    ): TaskRepository =
        TaskRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
        )


}
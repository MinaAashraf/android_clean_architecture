package com.ma.development.todo_app.di

import android.content.Context
import androidx.room.Room
import com.ma.development.todo_app.data.local.taskdb.TaskDao
import com.ma.development.todo_app.data.local.taskdb.TaskDatabase
import com.ma.development.todo_app.presentation.utils.TASK_LOCAL_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideTaskDatabase(@ApplicationContext context: Context): TaskDatabase =
        Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            TASK_LOCAL_DATABASE_NAME
        ).fallbackToDestructiveMigration().build()


    @Singleton
    @Provides
    fun provideTaskDao(taskDatabase: TaskDatabase): TaskDao = taskDatabase.getDao()


}
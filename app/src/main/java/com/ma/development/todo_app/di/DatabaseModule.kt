package com.ma.development.todo_app.di

import android.content.Context
import androidx.room.Room
import com.ma.development.todo_app.data.Entities.TaskData
import com.ma.development.todo_app.data.local.taskdb.TaskDao
import com.ma.development.todo_app.data.local.taskdb.TaskDatabase
import com.ma.development.todo_app.utils.TASK_LOCAL_DATABASE_NAME
import com.ma.development.todo_app.utils.TODO_API_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
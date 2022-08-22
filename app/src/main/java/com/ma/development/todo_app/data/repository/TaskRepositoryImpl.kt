package com.ma.development.todo_app.data.repository

import androidx.lifecycle.LiveData
import com.ma.development.todo_app.data.Entities.*
import com.ma.development.todo_app.domain.utils.Result
import com.ma.development.todo_app.data.local.TaskLocalDataSource
import com.ma.development.todo_app.data.remote.api.TaskRemoteDataSource
import com.ma.development.todo_app.domain.model.Task
import com.ma.development.todo_app.domain.utils.onFailure
import com.ma.development.todo_app.domain.utils.onSuccess
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val remoteDataSource: TaskRemoteDataSource,
    private val localDataSource: TaskLocalDataSource,
) : TaskRepository {

    override fun getAllTasks(): LiveData<List<Task>> {
        return localDataSource.getAllTasks()
    }

    override fun getCompletedTasks(): LiveData<List<Task>> =
        localDataSource.getCompletedTasks()

    override fun getInCompleteTasks(): LiveData<List<Task>> =
        localDataSource.getInCompletedTasks()

    override fun getTask(id: String): LiveData<Task> = localDataSource.getTask(id)


    override suspend fun updateTask(isCompleted: Boolean, id: String): Result<String> {
        localDataSource.updateTask(isCompleted, id)
        return remoteDataSource.updateTask(TaskStatus(isCompleted), id)
    }

    override suspend fun deleteTask(task: Task): Result<String> {
        localDataSource.deleteTask(task)
        return remoteDataSource.deleteTask(task.id)
    }

    override suspend fun addTask(description: TaskDescription): Result<Task> =
        remoteDataSource.addTask(description).onSuccess {
            localDataSource.addTask(it)
        }

    override suspend fun refreshLocalData(limit: Int, skip: Int): Result<Int> {
        var result: Result<Int> = Result.Success(0)
        remoteDataSource.getTasks(limit, skip).onSuccess {
            localDataSource.addTasks(it)
            result = Result.Success(it.size)
        }.onFailure {
            result = Result.Failure(it)
        }
        return result
    }


    /* val result = remoteDataSource.getTasks(limit, skip)
     return if (result is Result.Success) {
         val dataTasks = result.data
         localDataSource.addTasks(dataTasks)
         Result.Success(dataTasks.size)
     } else
         Result.Failure((result as Result.Failure).exception)
*/
    /* if (response.isSuccessful) {
         if (isNotModifiedResponseCode(response)) {
             return 0
         }
         return response.body()?.let {
             val notNullContentsTasks = it.data.filter { taskData -> taskData.hasNotNullData() }
             localDataSource.addTasks(listDataToListDomainMapper.mapTo(notNullContentsTasks))
             it.count
         } ?: 0

     }*/
    //}


}
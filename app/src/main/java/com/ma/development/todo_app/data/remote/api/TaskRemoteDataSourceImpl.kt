package com.ma.development.todo_app.data.remote.api

import com.ma.development.todo_app.data.DataNotAvailableException
import com.ma.development.todo_app.data.Entities.*
import com.ma.development.todo_app.domain.util.Result
import com.ma.development.todo_app.domain.mapper.IMapper
import com.ma.development.todo_app.domain.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class TaskRemoteDataSourceImpl @Inject constructor(
    private val taskApi: TaskApi,
    private val listDataToListDomainMapper: IMapper<List<TaskData>, List<Task>>,
    private val dataToDomainMapper: IMapper<TaskData, Task>,
) :
    TaskRemoteDataSource {

    override suspend fun getTasks(limit: Int, skip: Int): Result<List<Task>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                taskApi.getTasks(limit, skip).body()?.let {
                    val dataTasks = it.data.filter { taskData -> taskData.hasNotNullData() }
                    Result.Success(listDataToListDomainMapper.mapTo(dataTasks))
                } ?: Result.Failure(DataNotAvailableException())
            } catch (e: Exception) {
                Result.Failure(e)
            }
        }


    override suspend fun getTask(id: String): Result<Task> = withContext(Dispatchers.IO) {
        return@withContext try {
            taskApi.getTask(id).body()?.let {
                Result.Success(dataToDomainMapper.mapTo(it))
            } ?: Result.Failure(DataNotAvailableException())
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun addTask(description: TaskDescription): Result<Task> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                taskApi.addTask(description).body()?.let {
                    Result.Success(dataToDomainMapper.mapTo(it.data))
                } ?: Result.Failure(DataNotAvailableException())
            } catch (e: Exception) {
                Result.Failure(e)
            }
        }

    override suspend fun updateTask(status: TaskStatus, id: String) = withContext(Dispatchers.IO) {
        try {
            taskApi.updateTask(status, id)
            Result.Success(id)

        } catch (e: Exception) {
            Result.Failure(e)
        }
    }


    override suspend fun deleteTask(id: String)  = withContext(Dispatchers.IO) {
       return@withContext try {
            taskApi.deleteTask(id)
            Result.Success(id)
        } catch (e: Exception) {
           Result.Failure(e)
       }
    }


    private fun isNotModifiedResponseCode(response: Response<TasksListResponse>): Boolean {
        var isNotModified = false
        response.raw().networkResponse()?.let {
            isNotModified = (it.code() == HttpURLConnection.HTTP_NOT_MODIFIED)
        }
        return isNotModified
    }

}
package com.ma.development.todo_app.data.remote.api

import com.ma.development.todo_app.data.Entities.*
import com.ma.development.todo_app.utils.TODO_API_AUTHORIZATION_HEADER
import com.ma.development.todo_app.utils.TODO_API_CONTENT_TYPE_HEADER
import retrofit2.Response
import retrofit2.http.*


interface TaskApi {

 @GET("task")
 suspend fun getTasks (@Query("limit") limit:Int , @Query("skip") skip:Int ) : Response<TasksListResponse>

 @GET("task/{id}")
 suspend fun getTask (@Path("id") id:String) : Response<TaskData>

 @POST("task")
 suspend fun addTask (@Body description: TaskDescription) : Response<TaskResponse>

 @PUT("task/{id}")
 suspend fun updateTask (@Body status: TaskStatus, @Path("id") id:String)

 @DELETE("task/{id}")
 suspend fun deleteTask (@Path("id") id:String)

}
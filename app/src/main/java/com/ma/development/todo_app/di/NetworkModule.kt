package com.ma.development.todo_app.di

import android.content.Context
import com.ma.development.todo_app.data.remote.api.TaskApi
import com.ma.development.todo_app.utils.CACHE_SIZE_BYTES
import com.ma.development.todo_app.utils.TODO_API_AUTHORIZATION_HEADER
import com.ma.development.todo_app.utils.TODO_API_BASE_URL
import com.ma.development.todo_app.utils.TODO_API_CONTENT_TYPE_HEADER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Singleton
    @Provides
    fun provideCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, CACHE_SIZE_BYTES)


    @Singleton
    @Provides
    fun provideOkHttpClient(cache: Cache): OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor {
            val request = it.request().newBuilder()
                .addHeader("Authorization", TODO_API_AUTHORIZATION_HEADER)
                .addHeader("Content-Type", TODO_API_CONTENT_TYPE_HEADER)
                .build()
            it.proceed(request)
        }
        .cache(cache).build()


    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(TODO_API_BASE_URL)
            .client(client)
            .build()

    @Singleton
    @Provides
    fun provideTaskApi(retrofit: Retrofit): TaskApi =
        retrofit.create(TaskApi::class.java)

}
package com.ivlieva.sstesttask.repository.remote_data_source.retrofit

import retrofit2.http.*

interface TaskRemoteDataSource {

    @GET("task.json")
    suspend fun getData(): DataNetworkEntity

    @POST("task.json")
    suspend fun saveTask(@Body task: TaskNetworkEntity)
    suspend fun saveTasks(tasks: List<TaskNetworkEntity>)

    @GET("task.json?orderBy=\"id\"&equalTo={task}")
    suspend fun getTaskById(@Path("task") id: String): TaskNetworkEntity
}

package com.ivlieva.sstesttask.repository.remote_data_source.retrofit

import com.ivlieva.sstesttask.repository.TaskDataSource
import retrofit2.http.*

interface TaskRemoteDataSource: TaskDataSource<TaskNetworkEntity> {

    @GET("task.json")
    suspend fun getData(): DataNetworkEntity

    @POST("task.json")
    override suspend fun saveTask(@Body task: TaskNetworkEntity)

    @GET("task.json?orderBy=\"id\"&equalTo={task}")
    suspend fun getTaskById(@Path("task") id: String): TaskNetworkEntity
}

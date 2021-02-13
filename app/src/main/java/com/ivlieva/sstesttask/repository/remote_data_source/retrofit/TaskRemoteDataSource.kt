package com.ivlieva.sstesttask.repository.remote_data_source.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskRemoteDataSource {

    @GET("task.json")
    suspend fun getData(): DataNetworkEntity

    @POST("task.json")
    fun saveTask(@Body task: TaskNetworkEntity)

    @GET("task.json?orderBy=\"id\"&equalTo={task}")
    suspend fun getTaskById(@Path("task") id: String): TaskNetworkEntity
}

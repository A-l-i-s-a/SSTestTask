package com.ivlieva.sstesttask.di.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ivlieva.sstesttask.repository.remote_data_source.retrofit.DataNetworkEntity
import com.ivlieva.sstesttask.repository.remote_data_source.retrofit.TaskNetworkEntity
import com.ivlieva.sstesttask.repository.remote_data_source.retrofit.TaskRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(DataNetworkEntity::class.java, DataParser())
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://testtask-773b2-default-rtdb.firebaseio.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))

    }

    @Singleton
    @Provides
    fun provideBlogService(retrofit: Retrofit.Builder): TaskRemoteDataSource {
        return retrofit
            .build()
            .create(TaskRemoteDataSource::class.java)
    }
}
package com.ivlieva.sstesttask.repository

import com.ivlieva.sstesttask.entyty.Task
import com.ivlieva.sstesttask.repository.local_data_source.realm.CacheMapper
import com.ivlieva.sstesttask.repository.local_data_source.realm.RealmDataSource
import com.ivlieva.sstesttask.repository.remote_data_source.retrofit.NetworkMapper
import com.ivlieva.sstesttask.repository.remote_data_source.retrofit.TaskRemoteDataSource
import com.ivlieva.sstesttask.util.DataState
import com.ivlieva.sstesttask.util.setId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskLocalDataSource: RealmDataSource,
    private val taskRemoteDataSource: TaskRemoteDataSource,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun getTasks(): Flow<DataState<List<Task>>> = flow {
        emit(DataState.Loading)
        try {
            val networkData = taskRemoteDataSource.getData()
            val tasks = networkMapper.mapFromDataEntity(networkData)
            for (task in tasks) {
                taskLocalDataSource.saveTask(cacheMapper.mapToEntity(task))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))

        } finally {
            val cacheTasks = taskLocalDataSource.getTasks()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cacheTasks)))
        }
    }

    suspend fun getTaskById(id: Long): Flow<DataState<Task>> = flow {
        emit(DataState.Loading)
        try {
            val cacheTask = taskLocalDataSource.getTaskById(id)
            emit(DataState.Success(cacheMapper.mapFromEntity(cacheTask)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getTasksByDate(date: Long): Flow<DataState<List<Task>>> = flow {
        emit(DataState.Loading)
        try {
            val cacheTasks = taskLocalDataSource.getTaskByDate(date)
            emit(DataState.Success(cacheMapper.mapFromEntityList(cacheTasks)))
        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }

    suspend fun createTask(task: Task): Flow<DataState<Task>> = flow {
//        try {
            emit(DataState.Loading)
            task.id = setId()
            taskRemoteDataSource.saveTask(networkMapper.mapToEntity(task))
            taskLocalDataSource.saveTask(cacheMapper.mapToEntity(task))
            emit(DataState.Success(task))
//        } catch (e: Exception) {
//            emit(DataState.Error(e))
//        }
    }
}
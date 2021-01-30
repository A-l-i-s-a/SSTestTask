package com.ivlieva.sstesttask.di

import com.ivlieva.sstesttask.repository.TaskRepository
import com.ivlieva.sstesttask.repository.local_data_source.realm.CacheMapper
import com.ivlieva.sstesttask.repository.local_data_source.realm.RealmDataSource
import com.ivlieva.sstesttask.repository.remote_data_source.retrofit.NetworkMapper
import com.ivlieva.sstesttask.repository.remote_data_source.retrofit.TaskRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(
        dataSource: RealmDataSource,
        remoteDataSource: TaskRemoteDataSource,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): TaskRepository {
        return TaskRepository(dataSource, remoteDataSource, cacheMapper, networkMapper)
    }
}
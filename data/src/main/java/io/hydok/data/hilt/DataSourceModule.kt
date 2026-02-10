package io.hydok.data.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.hydok.data.datasource.LocalDataSource
import io.hydok.data.datasource.LocalDataSourceImpl
import io.hydok.data.datasource.RemoteDataSource
import io.hydok.data.datasource.RemoteDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    abstract fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource
}
package io.hydok.data.repositoryImpl

import io.hydok.data.datasource.LocalDataSource
import io.hydok.data.datasource.RemoteDataSource
import io.hydok.data.mapper.toSampleModels
import io.hydok.domain.model.SampleModel
import io.hydok.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {
    override fun getSampleData(): List<SampleModel> {
        return localDataSource.getSampleLocalData().toSampleModels()
    }
}
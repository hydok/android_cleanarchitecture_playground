package io.hydok.data.datasource

import io.hydok.data.entity.SampleEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor() : LocalDataSource {
    override fun getSampleLocalData(): List<SampleEntity> {
        // TODO: get Data from Room
        return listOf(
            SampleEntity("1", "name1"),
            SampleEntity("2", "name2"),
            SampleEntity("3", "name3")
        )
    }
}
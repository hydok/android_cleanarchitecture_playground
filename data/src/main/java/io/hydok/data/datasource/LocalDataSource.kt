package io.hydok.data.datasource

import io.hydok.data.entity.SampleEntity

interface LocalDataSource {
    fun getSampleLocalData(): List<SampleEntity>
}
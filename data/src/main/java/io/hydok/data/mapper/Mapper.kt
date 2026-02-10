package io.hydok.data.mapper

import io.hydok.data.entity.SampleEntity
import io.hydok.domain.model.SampleModel

fun List<SampleEntity>.toSampleModels(): List<SampleModel> {
    return map { SampleModel(it.id.toIntOrNull() ?: 0, it.name) }
}
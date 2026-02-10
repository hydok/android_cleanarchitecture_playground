package io.hydok.data.usecaseImpl

import io.hydok.domain.model.SampleModel
import io.hydok.domain.repository.Repository
import io.hydok.domain.usecase.GetSampleDataUseCase
import javax.inject.Inject

class GetSampleDataUseCaseImpl @Inject constructor(
    private val repository: Repository
) : GetSampleDataUseCase {
    override fun invoke(query: String): List<SampleModel> {
        return repository.getSampleData()
    }
}
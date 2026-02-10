package io.hydok.presentation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.hydok.domain.model.SampleModel
import io.hydok.domain.usecase.GetSampleDataUseCase
import io.hydok.presentation.view.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSampleDataUseCase: GetSampleDataUseCase
) : BaseViewModel() {

    private val _sampleData = MutableLiveData<List<SampleModel>>()
    val sampleData: LiveData<List<SampleModel>> get() = _sampleData

    fun fetchSampleData(query: String) {
        launchViewModelScope {
            showProgress()
            val result = getSampleDataUseCase(query)
            _sampleData.postValue(result)
            hideProgress()
        }
    }
}

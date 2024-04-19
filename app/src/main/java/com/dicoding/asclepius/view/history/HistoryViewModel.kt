package com.dicoding.asclepius.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.core.ResultState
import com.dicoding.asclepius.core.data.repository.AppRepository
import com.dicoding.asclepius.core.data.source.local.entity.PredictionResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
	private val repository: AppRepository
) : ViewModel() {

	private val _allHistory = MutableLiveData<ResultState<List<PredictionResult>>>(ResultState.Loading)
	val allHistory : LiveData<ResultState<List<PredictionResult>>> = _allHistory

	fun getAllHistory() {
		viewModelScope.launch(Dispatchers.IO) {
			repository.getAllHistory()
				.catch {error ->
					_allHistory.postValue(ResultState.Error(error.localizedMessage))
				}
				.collect { predictions ->
					_allHistory.postValue(ResultState.Success(predictions))
				}
		}
	}
	fun deleteHistory(prediction: PredictionResult) {
		viewModelScope.launch(Dispatchers.IO) {
			repository.deleteHistory(prediction)
		}
	}

	fun insertHistory(prediction: PredictionResult) {
		viewModelScope.launch(Dispatchers.IO) {
			repository.insertHistory(prediction)
		}
	}
}
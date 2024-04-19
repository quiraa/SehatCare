package com.dicoding.asclepius.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.core.ResultState
import com.dicoding.asclepius.core.data.source.remote.ApiService
import com.dicoding.asclepius.core.data.source.remote.response.HealthNewsResponse
import com.dicoding.asclepius.utils.errorParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val api: ApiService
) : ViewModel() {

	private val _newsState = MutableLiveData<ResultState<HealthNewsResponse>>(ResultState.Loading)
	val newsState: LiveData<ResultState<HealthNewsResponse>> = _newsState

	fun getTopHeadlines() {
		_newsState.postValue(ResultState.Loading)
		viewModelScope.launch(Dispatchers.IO) {
			try {
				val response = api.getHealthTopHeadlines()
				_newsState.postValue(ResultState.Success(response))
			} catch (error: Exception) {
				_newsState.postValue(ResultState.Error(errorParser(error)))
			}
		}
	}
}
package com.example.flicks.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flicks.API_KEY
import com.example.flicks.models.Result
import com.example.flicks.network.TMDbApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()

    val status: LiveData<String>
        get() = _status

    private val _resultData = MutableLiveData<List<Result>>()

    val resultData: LiveData<List<Result>>
        get() = _resultData

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getMovies()
    }

    private fun getMovies() {
        coroutineScope.launch {

            var getPropertiesDeferred = TMDbApi.retrofitService.getMoviesAsync(API_KEY)
            try {

                var listResult = getPropertiesDeferred.await()
                _status.value = "Success: ${listResult.results.size} Mars properties retrieved"
                if (listResult.results.size > 0) {
                    _resultData.value = listResult.results
                }

            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
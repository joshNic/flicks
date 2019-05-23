package com.example.flicks.overview

import android.util.Log
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
import java.util.*

enum class MoviesApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<MoviesApiStatus>()

    val status: LiveData<MoviesApiStatus>
        get() = _status

    private val _resultData = MutableLiveData<List<Result>>()

    val resultData: LiveData<List<Result>>
        get() = _resultData

    private val _navigateToSelectedMovie = MutableLiveData<Result>()
    val navigateToSelectedMovie: LiveData<Result>
    get() = _navigateToSelectedMovie

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getMovies()
    }

    private fun getMovies() {
        coroutineScope.launch {

            var getPropertiesDeferred = TMDbApi.retrofitService.getMoviesAsync(API_KEY)
            try {

                _status.value = MoviesApiStatus.LOADING

                var listResult = getPropertiesDeferred.await()

                if (listResult.results.isNotEmpty()) {
                    _status.value = MoviesApiStatus.DONE
                    _resultData.value = listResult.results
                }

            } catch (e: Exception) {
                _status.value = MoviesApiStatus.ERROR
//                _resultData.value = ArrayList()
                Log.i("errorMovie", e.toString())

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    fun displayPropertyDetails(movieProperty: Result){
        _navigateToSelectedMovie.value = movieProperty
    }
    fun displayPropertyDetailsComplete(){
        _navigateToSelectedMovie.value = null
    }

}
package com.example.flicks.movieDetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flicks.Constants
import com.example.flicks.models.MovieTrailerResult
import com.example.flicks.models.Result
import com.example.flicks.network.MovieApiFilter
import com.example.flicks.network.TMDbApi
import com.example.flicks.overview.MoviesApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDetailViewModel(resultProperty: Result, app: Application) : AndroidViewModel(app){
    private val _selectedProperty = MutableLiveData<Result>()
    val selectedProperty: LiveData<Result>
    get() = _selectedProperty

    private val _trailerResultData = MutableLiveData<List<MovieTrailerResult>>()

    val trailerResultData: LiveData<List<MovieTrailerResult>>
        get() = _trailerResultData

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        _selectedProperty.value = resultProperty
        getMovieTrailers(selectedProperty.value?.id.toString())
    }

    private fun getMovieTrailers(movieId:String) {
        coroutineScope.launch {

            var getPropertiesDeferred = TMDbApi.retrofitService.getMovieTrailersAsync(movieId, Constants.API_KEY)
            try {


                var listResult = getPropertiesDeferred.await()

                if (listResult.results.isNotEmpty()) {

                    _trailerResultData.value = listResult.results
                    Log.i("ResultMovieTrailer", _trailerResultData.value?.size.toString())
                }

            } catch (e: Exception) {
                Log.i("errorMovie", e.toString())

            }
        }
    }

}
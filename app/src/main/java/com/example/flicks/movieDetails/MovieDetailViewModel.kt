package com.example.flicks.movieDetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flicks.Constants
import com.example.flicks.models.Comment
import com.example.flicks.models.Genre
import com.example.flicks.models.MovieTrailerResult
import com.example.flicks.models.Result
import com.example.flicks.network.TMDbApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieDetailViewModel(resultProperty: Result, app: Application) : AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<Result>()
    val selectedProperty: LiveData<Result>
        get() = _selectedProperty

    private val _trailerResultData = MutableLiveData<List<MovieTrailerResult>>()

    val trailerResultData: LiveData<List<MovieTrailerResult>>
        get() = _trailerResultData

    private val _commentsResultData = MutableLiveData<List<Comment>>()

    val commentsResultData: LiveData<List<Comment>>
        get() = _commentsResultData

    private val _genresResultData = MutableLiveData<List<Genre>>()

    val genresResultData: LiveData<List<Genre>>
        get() = _genresResultData

    private val _navigateToSelectedTrailer = MutableLiveData<MovieTrailerResult>()
    val navigateToSelectedTrailer: LiveData<MovieTrailerResult>
        get() = _navigateToSelectedTrailer

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _selectedProperty.value = resultProperty
        getMovieTrailers(selectedProperty.value?.id.toString())
        getMovieGenre(selectedProperty.value!!.genreIds)
        getMovieComments(selectedProperty.value?.id.toString())

    }

    private fun getMovieTrailers(movieId: String) {
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

    private fun getMovieComments(movieId: String) {
        coroutineScope.launch {

            var getPropertiesDeferred = TMDbApi.retrofitService.getMovieCommentsAsync(movieId, Constants.API_KEY)
            try {
                var listResult = getPropertiesDeferred.await()

                if (listResult.results.isNotEmpty()) {

                    _commentsResultData.value = listResult.results

                    Log.i("ResultMovieComments", _commentsResultData.value?.size.toString())
                }

            } catch (e: Exception) {
                Log.i("errorMovieComments", e.toString())

            }
        }
    }

    private fun getMovieGenre(genreList: List<Int>) {
        coroutineScope.launch {

            var getGenreDeferred = TMDbApi.retrofitService.getMovieGenresAsync(Constants.API_KEY)
            try {

                var genreListResult = getGenreDeferred.await()

                if (genreListResult.genres.isNotEmpty()) {
                    _genresResultData.value = genreListResult.genres

                    var newGenresList = _genresResultData.value?.filter {
                        it.id in genreList
                    }
                    _genresResultData.value = newGenresList
                    Log.i("Gener",genresResultData.value.toString())
                    Log.i("Generrr",genresResultData.value?.size.toString())
                }
            } catch (e: Exception) {
                Log.i("errorMovie", e.toString())

            }
        }
    }

    fun displayMovieTrailer(trailerProperty: MovieTrailerResult) {
        _navigateToSelectedTrailer.value = trailerProperty
    }

    fun displayMovieTrailerComplete() {
        _navigateToSelectedTrailer.value = null
    }


}
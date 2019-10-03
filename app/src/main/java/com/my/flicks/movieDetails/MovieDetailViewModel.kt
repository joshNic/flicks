package com.my.flicks.movieDetails

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.my.flicks.Constants
import com.my.flicks.database.MovieDatabaseDao
import com.my.flicks.models.Comment
import com.my.flicks.models.Genre
import com.my.flicks.models.MovieTrailerResult
import com.my.flicks.models.Result
import com.my.flicks.network.TMDbApi
import kotlinx.coroutines.*

class MovieDetailViewModel(
    resultProperty: Result,
    app: Application,
    val database: MovieDatabaseDao
) :
    AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<Result>()

    var dbData = database.getAllMovies()
    var isVisible = ObservableBoolean(false)

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

        //onStartTrack()

//        val wordsDao = MovieDatabase.getInstance(app).movieDatabaseDao()
//        repositor = MovieRepository(wordsDao)

    }

    private fun getMovieTrailers(movieId: String) {
        coroutineScope.launch {

            var getPropertiesDeferred =
                TMDbApi.retrofitService.getMovieTrailersAsync(movieId, Constants.API_KEY)
            try {
                var listResult = getPropertiesDeferred.await()

                if (listResult.results.isNotEmpty()) {

                    _trailerResultData.value = listResult.results

//                    Log.i("ResultMovieTrailer", _trailerResultData.value?.size.toString())
                }

            } catch (e: Exception) {
            }
        }
    }

    suspend fun insertMovie(result: Result) {
        withContext(Dispatchers.IO) {
            database.insertMovie(result)
        }
    }

     fun getMovi(movieId: Int):Int {
        var len = 0
        runBlocking {
            withContext(Dispatchers.IO) {
                if (!database.getMovie(movieId).toString().isNullOrEmpty()){
                    len = database.getMovie(movieId).toString().length
                }
            }
        }
        return len
    }
    fun getMove(): Int = getMovi(selectedProperty.value!!.id)
    suspend fun removeFavourite(movieId: Int){
        withContext(Dispatchers.IO) {
           database.deleteByMovieId(movieId)
        }
    }
    fun removeMovieFromDatabase() {
        coroutineScope.launch {
            removeFavourite(selectedProperty.value!!.id)
            Toast.makeText(getApplication(), "Movie Removed From Favourites", Toast.LENGTH_SHORT).show()
        }
    }

    fun getAllDatabaseMovies() {
        //db = database.getAllMovies()
//        Log.i("This is data", dbData.value.toString())
//        db = database.getAllMovies()
//        Log.i("databaseSize", db!!.value.toString())
//        withContext(Dispatchers.IO) {
//
////            db.value = database.getAllMovies().value as Result?
//            Log.i("This is data", db.value.toString())
////            database.getAllMovies().value
//        }
    }

    fun addMovieToDatabase() {
        coroutineScope.launch {
            insertMovie(selectedProperty.value!!)
            Toast.makeText(getApplication(), "Movie Added To Favourites", Toast.LENGTH_SHORT).show()
        }
    }


//    fun getMovieFromDatabase() = runBlocking{
//        getMovie(selectedProperty.value!!.id)
//    }
//        var movieId=0

//                 getMovie(selectedProperty.value!!.id)
//
////            insertMovie(selectedProperty.value!!)
//
//            Toast.makeText(getApplication(),"Movie Added To Favourites", Toast.LENGTH_SHORT).show()
//        }
//        return movieId
//    }

    private fun getMovieComments(movieId: String) {
        coroutineScope.launch {

            var getPropertiesDeferred =
                TMDbApi.retrofitService.getMovieCommentsAsync(movieId, Constants.API_KEY)
            try {
                var listResult = getPropertiesDeferred.await()

                if (listResult.results.isNotEmpty()) {

                    _commentsResultData.value = listResult.results
                    isVisible.set(false)
//                    Log.i("ResultMovieComments", _commentsResultData.value?.size.toString())
                } else {
                    isVisible.set(true)
                }

            } catch (e: Exception) {

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
//                    Log.i("Gener", genresResultData.value.toString())
//                    Log.i("Generrr", genresResultData.value?.size.toString())
                }
            } catch (e: Exception) {
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
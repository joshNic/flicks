package com.my.flicks.overview

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.my.flicks.database.MovieDatabaseDao
import com.my.flicks.models.Result
import com.my.flicks.network.MovieApiFilter
import com.my.flicks.repository.MoviePagedListRepository
import kotlinx.coroutines.Job

enum class MoviesApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel(val dataSource:MovieDatabaseDao, application: Application ): AndroidViewModel(application) {

    private val _status = MutableLiveData<MoviesApiStatus>()

    var dbData = dataSource.getAllMovies().toLiveData(50)
    var dbDataQuery = dataSource.getAllWords()

    val status: LiveData<MoviesApiStatus>
        get() = _status

    private val _resultData = MutableLiveData<List<Result>>()

    val resultData: LiveData<List<Result>>
        get() = _resultData

    private val _navigateToSelectedMovie = MutableLiveData<Result>()
    val navigateToSelectedMovie: LiveData<Result>
        get() = _navigateToSelectedMovie

    private var viewModelJob = Job()


    var movieRepository: MoviePagedListRepository =
        MoviePagedListRepository(MovieApiFilter.MOST_POPULAR.path)


    init {
        getAllMovies()
    }

    fun start() = movieRepository.fetchLiveMoviePagedList()


    fun status() = movieRepository.getStatus()

    private fun getMovies(filter: MovieApiFilter) {
        if (movieRepository.path != filter.path)
            movieRepository.path = filter.path
            movieRepository.refreshData(filter.path)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayPropertyDetails(movieProperty: Result) {
        _navigateToSelectedMovie.value = movieProperty
    }
    fun getAllMovies(): LiveData<PagedList<Result>> = dbData

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }

    fun updateFilter(filter: MovieApiFilter) {
        getMovies(filter)
    }

}
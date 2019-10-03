package com.my.flicks.movieDetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.my.flicks.database.MovieDatabaseDao
import com.my.flicks.models.Result

class DetailViewModelFactory(
    private val resultProperty: Result,
    private val application: Application,
    private val dataSource: MovieDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(resultProperty, application,dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
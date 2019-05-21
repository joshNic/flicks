package com.example.flicks.movieDetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flicks.models.Result

class DetailViewModelFactory(
    private val resultProperty: Result,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(resultProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
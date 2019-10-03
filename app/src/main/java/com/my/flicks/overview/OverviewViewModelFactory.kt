package com.my.flicks.overview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.my.flicks.database.MovieDatabaseDao

class OverviewViewModelFactory(
    private val application: Application,
    private val dataSource: MovieDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            return OverviewViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
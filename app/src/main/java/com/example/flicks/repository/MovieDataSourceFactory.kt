package com.example.flicks.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.flicks.models.Result

class MovieDataSourceFactory(var path: String) :
    DataSource.Factory<Int, Result>() {
    val sourceLiveData = MutableLiveData<MovieDataSource>()
    override fun create(): DataSource<Int, Result> {
        val movieDataSource = MovieDataSource(path)

        sourceLiveData.postValue(movieDataSource)
        return movieDataSource
    }
}

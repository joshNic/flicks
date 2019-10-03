package com.my.flicks.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.my.flicks.models.Result

class MoviePagedListRepository(var path: String) {
    lateinit var moviePagedList: LiveData<PagedList<Result>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList () : LiveData<PagedList<Result>> {
        moviesDataSourceFactory = MovieDataSourceFactory(path)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun refreshData(new_path: String) {
        moviesDataSourceFactory.path = new_path
        moviesDataSourceFactory.sourceLiveData.value?.invalidate()
    }

    fun getStatus() = moviesDataSourceFactory.loading

}

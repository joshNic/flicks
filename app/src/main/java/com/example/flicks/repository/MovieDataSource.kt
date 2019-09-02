package com.example.flicks.repository

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.flicks.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.flicks.models.Result
import com.example.flicks.network.TMDbApi

class MovieDataSource(var path: String) :
    PageKeyedDataSource<Int, Result>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) {
        GlobalScope.launch (Dispatchers.IO) {
            var PAGE = 1
            try {
                var getPropertiesDeferred =
                    TMDbApi.retrofitService.getMoviesAsync(
                        path,
                        Constants.API_KEY,
                        PAGE.toString()
                    )
                var listResult = getPropertiesDeferred.await()
                callback.onResult(listResult.results, null, PAGE + 1)
            } catch (e: Exception) {
                callback.onResult(ArrayList(), null, PAGE + 1)
                Log.e("Error", "Error while fetching data.")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        GlobalScope.launch (Dispatchers.IO) {
            try {
                var getPropertiesDeferred =
                    TMDbApi.retrofitService.getMoviesAsync(
                        path,
                        Constants.API_KEY,
                        params.key.toString()
                    )

                var listResult = getPropertiesDeferred.await()
                callback.onResult(listResult.results, params.key + 1)
            } catch (e: Exception) {
                Log.e("Error", "Error while fetching data.")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
    }
}

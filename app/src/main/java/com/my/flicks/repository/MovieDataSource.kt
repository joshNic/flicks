package com.my.flicks.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.my.flicks.Constants
import com.my.flicks.models.Result
import com.my.flicks.network.TMDbApi
import kotlinx.coroutines.*

class MovieDataSource(var path: String, var loading: MutableLiveData<Boolean>) :
    PageKeyedDataSource<Int, Result>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            var PAGE = 1
            withContext(Dispatchers.Main){ loading.value = true }
            try {
                var getPropertiesDeferred =
                    TMDbApi.retrofitService.getMoviesAsync(
                        path,
                        Constants.API_KEY,
                        PAGE.toString()
                    )
                var listResult = getPropertiesDeferred.await()
                withContext(Dispatchers.Main){ loading.value = false }
                callback.onResult(listResult.results, null, PAGE + 1)
            } catch (e: Exception) {
                withContext(Dispatchers.Main){ loading.value = false }
                callback.onResult(ArrayList(), null, PAGE + 1)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        CoroutineScope(Dispatchers.IO).launch {
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
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
    }
}

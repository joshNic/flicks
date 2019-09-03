package com.example.flicks.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.flicks.Constants
import com.example.flicks.models.Result
import com.example.flicks.network.TMDbApi
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
                Log.e("Error", "Error while fetching data.")
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
                Log.e("Error", "Error while fetching data.")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
    }
}

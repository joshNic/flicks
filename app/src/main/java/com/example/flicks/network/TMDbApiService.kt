package com.example.flicks.network

import com.example.flicks.models.MovieResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/"

enum class MovieApiFilter(val path: String) {
    MOST_POPULAR("popular"), TOP_RATED("top_rated"), UPCOMING("upcoming"), NOW_PLAYING(
        "now_playing"
    )
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface TMDbApiService {

    @GET("3/movie/{path}")
    fun getMoviesAsync(
        @Path("path") path: String,
        @Query("api_key") type: String
    ):
            Deferred<MovieResponse>
}

object TMDbApi {
    val retrofitService: TMDbApiService by lazy { retrofit.create(TMDbApiService::class.java) }
}
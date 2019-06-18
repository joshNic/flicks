package com.example.flicks.database

import androidx.lifecycle.LiveData
import com.example.flicks.models.Result

class MovieRepository(private val movieDao: MovieDatabaseDao) {
    val allMovies = movieDao.getAllMovies()

    suspend fun insert(movie: Result) {
        movieDao.insertMovie(movie)
    }

}
package com.my.flicks.database

import com.my.flicks.models.Result

class MovieRepository(private val movieDao: MovieDatabaseDao) {
    val allMovies = movieDao.getAllMovies()

    suspend fun insert(movie: Result) {
        movieDao.insertMovie(movie)
    }

}
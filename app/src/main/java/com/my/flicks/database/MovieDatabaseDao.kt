package com.my.flicks.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.my.flicks.models.Result


@Dao
interface MovieDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Result)

    @Query("SELECT * from movie_table WHERE id = :key")
    fun getMovie(key: Int): Result?
//
//    @Query("DELETE FROM movie_table WHERE id= :key")
//    fun deleteMovie(key: Int): Result?
    @Query("DELETE FROM movie_table WHERE id= :key")
    fun deleteByMovieId(key: Int)

    @Query("SELECT * from movie_table")
    fun getAllMovies(): DataSource.Factory<Int, Result>

    @Query("SELECT * from movie_table")
    fun getAllWords(): LiveData<List<Result>>

//    @Query("SELECT * from movie_table where id = :id")
//    fun getMovieById(id: Int?): Result?
}


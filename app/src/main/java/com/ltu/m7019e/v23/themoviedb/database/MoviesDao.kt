package com.ltu.m7019e.v23.themoviedb.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ltu.m7019e.v23.themoviedb.model.Movie

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movieList: List<Movie>)

    @Query("DELETE FROM Movies")
    fun deleteAllMovies()

    @Query("SELECT * from Movies ORDER BY id ASC")
    fun getAllMovies(): LiveData<List<Movie>>
}
package com.ltu.m7019e.v23.themoviedb.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ltu.m7019e.v23.themoviedb.model.*

@Dao
interface MoviesDao {
    // Movie
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movieList: List<Movie>)

    @Query("DELETE FROM movies")
    fun deleteAllMovies()

    @Query("SELECT * from movies ORDER BY id ASC")
    fun getAllMovies(): LiveData<List<Movie>>

    // saved movie
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(savedMovie: SavedMovie)

    @Query("DELETE FROM savedMovies WHERE id = :id")
    fun delete(id: Long)

    @Query("SELECT EXISTS(SELECT * from savedMovies WHERE id = :id)")
    suspend fun isFavorite(id: Long): Boolean

    @Query("SELECT * from savedMovies ORDER BY id ASC")
    fun getAllSavedMovies(): List<SavedMovie>
}
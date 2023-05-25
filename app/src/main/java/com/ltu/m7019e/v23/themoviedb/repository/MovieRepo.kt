package com.ltu.m7019e.v23.themoviedb.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.ltu.m7019e.v23.themoviedb.database.*
import com.ltu.m7019e.v23.themoviedb.model.*
import com.ltu.m7019e.v23.themoviedb.network.NetworkStatus
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import kotlinx.coroutines.*
import java.io.IOException

class MovieRepo(private val database: Movies)
{
    private var movieList: LiveData<List<Movie>> = database.moviesDao.getAllMovies();

    suspend fun getMovies(mode: Int)
    {
        withContext(Dispatchers.IO)
        {
            var list = listOf<Movie>()
            try {
                when (mode) {
                    0 -> {
                        list = TMDBApi.movieListRetrofitService.getPopularMovies().results
                    }
                    1 -> {
                        list = TMDBApi.movieListRetrofitService.getTopRatedMovies().results
                    }
                    2 -> {
                        list = database.moviesDao.getAllSavedMovies().map { savedMovie ->
                            Movie(
                                id = savedMovie.id,
                                title = savedMovie.title,
                                posterPath = savedMovie.posterPath,
                                backdropPath = savedMovie.backdropPath,
                                releaseDate = savedMovie.releaseDate,
                                overview = savedMovie.overview
                            )
                        }
                    }
                }
            }
            catch (Error: IOException)
            {
                list = requireNotNull(database.moviesDao.getAllMovies().value)
            }
            database.moviesDao.deleteAllMovies()
            list.forEach{movie ->
                database.moviesDao.insert(movie)
            }
        }
    }
}
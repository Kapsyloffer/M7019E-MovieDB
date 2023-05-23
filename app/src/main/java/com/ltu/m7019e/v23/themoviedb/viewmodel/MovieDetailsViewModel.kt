package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.accounts.NetworkErrorException
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.database.*
import com.ltu.m7019e.v23.themoviedb.model.*
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MovieDetailsViewModel(application: Application, private val Dao : MoviesDao) : AndroidViewModel(application) {

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() = _dataFetchStatus

    private val _imdbLiveData = MutableLiveData<String>()
    val imdbLiveData: LiveData<String>
        get() = _imdbLiveData

    suspend fun fetchIMDB(movie: Movie): String {
        try {
            val id = TMDBApi.movieListRetrofitService.getMovieDetails(movie.id.toLong()).imdb_id
            Log.i("IMDB: ", id.toString())
            return id
        } catch (error: NetworkErrorException) {
            Log.e("Error", error.toString())
            return "Error"
        }
    }

    fun saveMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO)
        {
            Dao.insert(
                SavedMovie(
                    id = movie.id,
                    title = movie.title,
                    posterPath = movie.posterPath,
                    backdropPath = movie.backdropPath,
                    releaseDate = movie.releaseDate,
                    overview = movie.overview
                )
            )
        }
    }

    fun unsaveMovie(id : Long)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            Dao.delete(id)
        }
    }

    suspend fun isSaved(movie: Movie): Boolean {
        val deferred = CompletableDeferred<Boolean>()

        withContext(Dispatchers.IO) {
            Dao.getAllSavedMovies().forEach { m ->
                if (movie.id == m.id) {
                    deferred.complete(true)
                    Log.i("A", "FOUND true")
                    return@withContext
                }
            }
            deferred.complete(false)
        }
        return deferred.await()
    }

}


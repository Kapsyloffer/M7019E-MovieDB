package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.accounts.NetworkErrorException
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import kotlinx.coroutines.launch
import java.io.IOException

class MovieDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() = _dataFetchStatus

    private val _imdbLiveData = MutableLiveData<String>()
    val imdbLiveData: LiveData<String>
        get() = _imdbLiveData

    suspend fun fetchIMDB(movie: Movie): String {
        return try {
            val id = TMDBApi.movieListRetrofitService.getMovieDetails(movie.id.toLong()).imdb_id
            Log.i("IMDB: ", id.toString())
            id
        } catch (error: NetworkErrorException) {
            Log.e("Error", error.toString())
            "Error"
        }
    }

}

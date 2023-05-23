package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.database.Movies
import com.ltu.m7019e.v23.themoviedb.database.MoviesDao
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.network.NetworkStatus
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MovieListViewModel(application: Application, private val Dao : MoviesDao) : AndroidViewModel(application) {

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() = _dataFetchStatus

    private val _navigateToMovieDetail = MutableLiveData<Movie?>()
    val navigateToMovieDetail: MutableLiveData<Movie?>
        get() = _navigateToMovieDetail

    // Use a MutableLiveData object for the movie list
    private val _movieList = MutableLiveData<List<Movie>>()

    // Expose a LiveData object for the movie list that can be observed by the UI
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    init {
        getMovies(0)
    }

    fun onMovieListItemClicked(movie: Movie) {
        _navigateToMovieDetail.value = movie
    }

    fun onMovieDetailNavigated() {
        _navigateToMovieDetail.value = null
    }

    private fun setMovieList(movieList: List<Movie>) {
        viewModelScope.launch(Dispatchers.Main) {
            _movieList.postValue(movieList)
        }
    }

    fun getMovies(mode: Int) {
        viewModelScope.launch {
            var context = getApplication<Application>().applicationContext
            if (!NetworkStatus.isInternetAvailable(context))
            {
                //getCached()
                return@launch
            }
            try {
                val list = mutableListOf<Movie>()
                when (mode) {
                    0 -> {
                        list.addAll(TMDBApi.movieListRetrofitService.getPopularMovies().results)
                    }
                    1 -> {
                        list.addAll(TMDBApi.movieListRetrofitService.getTopRatedMovies().results)
                    }
                    2 -> {
                        fetchSaved()
                    }
                }
                setMovieList(list)
            } catch (networkError: IOException) {
                _dataFetchStatus.postValue(DataFetchStatus.Error)
            }
        }
    }

    private suspend fun fetchSaved() {
        withContext(Dispatchers.IO) {
            val list = mutableListOf<Movie>()
            for (savedMovie in Dao.getAllSavedMovies()) {
                list.add(
                    Movie(
                        id = savedMovie.id,
                        title = savedMovie.title,
                        posterPath = savedMovie.posterPath,
                        backdropPath = savedMovie.backdropPath,
                        releaseDate = savedMovie.releaseDate,
                        overview = savedMovie.overview
                    )
                )
                Log.i("a", list.toString())
            }
            setMovieList(list)
        }
    }
}

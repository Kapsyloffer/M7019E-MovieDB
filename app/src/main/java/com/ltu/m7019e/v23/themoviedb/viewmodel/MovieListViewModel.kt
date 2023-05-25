package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
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

    private var latest = 0;

    // Expose a LiveData object for the movie list that can be observed by the UI
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    init {
        getMovies(null)
    }

    fun onMovieListItemClicked(movie: Movie) {
        _navigateToMovieDetail.value = movie
    }

    fun onMovieDetailNavigated() {
        _navigateToMovieDetail.value = null
    }

    private fun setMovieList(movieList: List<Movie>) {
        viewModelScope.launch(Dispatchers.Main) {
            Log.i("List: ", movieList.toString())
            _movieList.value = movieList
        }
    }

    fun getMovies(mode: Int?)
    {
        if(mode == null)
        {
            getMovies(latest)
            return
        }
        latest = mode;
        viewModelScope.launch {
            var context = getApplication<Application>().applicationContext
            var hasInternet = NetworkStatus.isInternetAvailable(context)
            if (!hasInternet)
            {
                //getCached()
                return@launch
            }
            try {
                val list = mutableListOf<Movie>()
                when (mode)
                {
                    0 -> {
                        list.addAll(TMDBApi.movieListRetrofitService.getPopularMovies().results)
                        setMovieList(list)
                    }
                    1 -> {
                        list.addAll(TMDBApi.movieListRetrofitService.getTopRatedMovies().results)
                        setMovieList(list)
                    }
                    2 -> {
                        getSaved()
                    }
                }
                //cacheList()
            } catch (networkError: IOException) {
                _dataFetchStatus.postValue(DataFetchStatus.Error)
            }
        }
    }

    private suspend fun getSaved() {
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
            }
            Log.i("LISTAN\n\n", list.toString())
            setMovieList(list)
        }
    }

    //Crashes, idk why
    private suspend fun cacheList() {
        withContext(Dispatchers.IO) {
            _movieList.value?.let { movieList ->
                Dao.deleteAllMovies()
                for (movie in movieList) {
                    viewModelScope.launch {
                        Dao.insert(
                            Movie(
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
            }
        }
    }
}

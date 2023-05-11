package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import kotlinx.coroutines.launch
import java.io.IOException

class MovieListViewModel(application: Application) : AndroidViewModel(application) {
    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() {
            return _movieList
        }

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _navigateToMovieDetail = MutableLiveData<Movie?>()
    val navigateToMovieDetail: MutableLiveData<Movie?>
        get() {
            return _navigateToMovieDetail
        }

    init {
        //_movieList.postValue(Movies().list)
        getMovies(true)
    }

    fun onMovieListItemClicked(movie: Movie) {
        _navigateToMovieDetail.value = movie
    }

    fun onMovieDetailNavigated() {
        _navigateToMovieDetail.value = null;
    }

    fun getMovies(pop : Boolean)
    {
        viewModelScope.launch {
            try {
                if (pop) { //If popular, else top rated
                    _movieList.value = TMDBApi.movieListRetrofitService.getPopularMovies().results;
                } else {
                    _movieList.value = TMDBApi.movieListRetrofitService.getTopRatedMovies().results;
                }
            } catch (NetworkError: IOException) {
                _dataFetchStatus.value = DataFetchStatus.Error
            }
        }
    }
}
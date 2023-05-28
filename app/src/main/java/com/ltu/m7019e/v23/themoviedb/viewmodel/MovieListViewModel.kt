package com.ltu.m7019e.v23.themoviedb.viewmodel

import Refresher
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.ltu.m7019e.v23.themoviedb.database.*
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.network.NetworkStatus
import com.ltu.m7019e.v23.themoviedb.repository.MovieRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MovieListViewModel(application: Application, private val database : Movies) : AndroidViewModel(application) {

    // Create an instance of MovieRepo by passing the required dependencies
    val movieRepo = MovieRepo(database)
    val Dao : MoviesDao = database.moviesDao
    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() = _dataFetchStatus

    private val _navigateToMovieDetail = MutableLiveData<Movie?>()
    val navigateToMovieDetail: MutableLiveData<Movie?>
        get() = _navigateToMovieDetail

    // Use a MutableLiveData object for the movie list
    private val _movieList = MutableLiveData<List<Movie>>()

    private var latest = 0;

    val getLatest : Int
        get() = latest

    init {
        getMovies(latest)
    }

    fun onMovieListItemClicked(movie: Movie) {
        _navigateToMovieDetail.value = movie
    }

    fun onMovieDetailNavigated() {
        _navigateToMovieDetail.value = null
    }

    private fun setMovieList(movieList: List<Movie>) {
        viewModelScope.launch(Dispatchers.Main) {
            //Log.i("List: ", movieList.toString())
            _movieList.value = movieList
        }
    }

    fun getMovies(mode: Int?)
    {
        if(mode != null)
        {
            latest = mode
        }
            viewModelScope.launch {
                var context = getApplication<Application>().applicationContext;
                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

                val workRequest: WorkRequest = OneTimeWorkRequestBuilder<Refresher>()
                    .setConstraints(constraints)
                    .build()
                    movieRepo.getMovies(latest, context)
                if(NetworkStatus.isInternetAvailable(context))
                {
                    WorkManager.getInstance(getApplication()).cancelAllWork()
                }
                else
                {
                    WorkManager.getInstance(getApplication()).enqueue(workRequest)
                }
            }
        }
    }

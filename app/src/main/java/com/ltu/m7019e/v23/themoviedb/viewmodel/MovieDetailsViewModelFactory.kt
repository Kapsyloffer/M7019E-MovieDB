package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ltu.m7019e.v23.themoviedb.database.MoviesDao
import java.lang.IllegalArgumentException

class MovieDetailsViewModelFactory(private val application: Application, private val Dao : MoviesDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(application, Dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
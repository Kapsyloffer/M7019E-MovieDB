package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ltu.m7019e.v23.themoviedb.database.*
import java.lang.IllegalArgumentException

class MovieListViewModelFactory(private val application: Application, private val database : Movies): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(application, database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
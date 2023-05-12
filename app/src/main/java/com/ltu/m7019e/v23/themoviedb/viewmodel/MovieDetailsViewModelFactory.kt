package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MovieDetailsViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
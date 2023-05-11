package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ltu.m7019e.v23.themoviedb.database.Review
import com.ltu.m7019e.v23.themoviedb.model.Movie

class ReviewViewModel(application: Application, movie: Movie) : AndroidViewModel(application) {
    private val _movieReviewList = MutableLiveData<List<Review>>()
    val movieReviewList: LiveData<List<Review>>
        get() {
            return _movieReviewList
        }
}
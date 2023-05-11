package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ltu.m7019e.v23.themoviedb.model.Review
import com.ltu.m7019e.v23.themoviedb.model.Movie

class ReviewViewModel(application: Application, movie: Movie) : AndroidViewModel(application) {
    private val _movieReviewList = MutableLiveData<List<Review>>()
    val movieReviewList: LiveData<List<Review>>
        get() {
            return _movieReviewList
        }

    fun addReviews() {
        val reviews = _movieReviewList.value?.toMutableList() ?: mutableListOf()
        val review1 = Review(1, "Author 1", "Content 1")
        val review2 = Review(2, "Author 2", "Content 2")
        val review3 = Review(3, "Author 1", "Content 1")
        val review4 = Review(4, "Author 2", "Content 2")
        reviews.add(review1)
        reviews.add(review2)
        reviews.add(review3)
        reviews.add(review4)
        _movieReviewList.value = reviews
    }
}
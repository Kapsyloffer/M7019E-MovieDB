package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.accounts.NetworkErrorException
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.model.Review
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import com.ltu.m7019e.v23.themoviedb.network.TMDBApiService
import kotlinx.coroutines.launch

class ReviewViewModel(application: Application, movie: Movie) : AndroidViewModel(application) {
    private val _movieReviewList = MutableLiveData<List<Review>>()
    val movieReviewList: LiveData<List<Review>>
        get() {
            return _movieReviewList
        }

   /* fun addReviews() {
        val reviews = _movieReviewList.value?.toMutableList() ?: mutableListOf()
        val review1 = Review(1, "Axel", "Den sög, 0/10")
        val review2 = Review(2, "Boris", "Liksom den var kinda good. Inget speciellt tho.")
        val review3 = Review(3, "Calle", "Perfekt, finns inget bättre!")
        val review4 = Review(4, "David", "Klockren!")
        reviews.add(review1)
        reviews.add(review2)
        reviews.add(review3)
        reviews.add(review4)
        _movieReviewList.value = reviews
    }*/

    fun getReviews(movie : Movie)
    {
        viewModelScope.launch {
            try {
                _movieReviewList.value = TMDBApi.movieListRetrofitService.getMovieReviews(movie.id.toLong()).results
            }
            catch (error: NetworkErrorException)
            {

            }
        }
    }
}
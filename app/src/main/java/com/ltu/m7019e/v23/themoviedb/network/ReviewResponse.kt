package com.ltu.m7019e.v23.themoviedb.network

import com.ltu.m7019e.v23.themoviedb.model.Review
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ReviewResponse {
    @Json(name = "results")
    var results : List<Review> = listOf()
}
package com.ltu.m7019e.v23.themoviedb.network

import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieResponse
{
     @Json(name = "Results")
     var results : List<Movie> = listOf()
}
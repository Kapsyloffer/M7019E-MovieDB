package com.ltu.m7019e.v23.themoviedb.network

import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieDetailResponse
{
     @Json(name = "imdb_id")
     var imdb_id : String = ""
     @Json(name = "homepage")
     var homepage : String = ""
}
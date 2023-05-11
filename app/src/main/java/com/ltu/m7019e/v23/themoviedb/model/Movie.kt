package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
        @Json(name = "id")
        var id: Int,
        @Json(name = "title")
        var title: String,
        @Json(name = "poster_path")
        var posterPath: String,
        @Json(name = "backdrop_path")
        var backdropPath: String,
        @Json(name = "release_date")
        var releaseDate: String,
        @Json(name = "overview")
        var overview: String
) : Parcelable
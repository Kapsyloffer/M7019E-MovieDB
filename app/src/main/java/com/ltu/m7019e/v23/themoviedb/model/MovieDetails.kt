package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetails(
    @Json(name = "id")
    var id: Int,
    @Json(name = "imdb_id")
    var imdb_id : String,
    @Json(name = "homepage")
    var homepage : String
) : Parcelable
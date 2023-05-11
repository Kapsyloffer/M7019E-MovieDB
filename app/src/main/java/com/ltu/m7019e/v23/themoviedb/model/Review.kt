package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
        @Json(name = "id")
        var id: Int,
        @Json(name = "author")
        var author: String,
        @Json(name = "content")
        var content: String
) : Parcelable
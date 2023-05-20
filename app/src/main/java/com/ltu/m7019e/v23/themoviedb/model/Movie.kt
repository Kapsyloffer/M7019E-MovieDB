package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import androidx.room.*
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Movies")
data class Movie(
        @PrimaryKey
        @Json(name = "id")
        var id: Int,

        @ColumnInfo(name = "title")
        @Json(name = "title")
        var title: String,

        @ColumnInfo(name = "poster_path")
        @Json(name = "poster_path")
        var posterPath: String,

        @ColumnInfo(name = "backdrop_patch")
        @Json(name = "backdrop_path")
        var backdropPath: String,

        @ColumnInfo(name = "release_date")
        @Json(name = "release_date")
        var releaseDate: String,

        @ColumnInfo(name = "overview")
        @Json(name = "overview")
        var overview: String
) : Parcelable
package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import androidx.room.*
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "MovieDetails")
data class MovieDetails(
    @PrimaryKey
    @Json(name = "id")
    var id: Int,

    @ColumnInfo(name = "imdb_id")
    @Json(name = "imdb_id")
    var imdb_id : String,

    @ColumnInfo(name = "homepage")
    @Json(name = "homepage")
    var homepage : String
) : Parcelable
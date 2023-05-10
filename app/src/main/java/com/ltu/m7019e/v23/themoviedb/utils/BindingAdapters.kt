package com.ltu.m7019e.v23.themoviedb.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("posterImageUrl")
fun bindPosterImage(imgView: ImageView, imgUrl:String) {
    imgUrl.let { posterPath ->
        Glide
            .with(imgView)
            .load(Contants.POSTER_IMAGE_BASE_URL + Contants.POSTER_IMAGE_WIDTH + posterPath)
            .into(imgView);
    }
}

@BindingAdapter("backdropImageUrl")
fun bindBackdropImage(imgView: ImageView, imgUrl:String) {
    imgUrl.let { backdropPath ->
        Glide
            .with(imgView)
            .load(Contants.BACKDROP_IMAGE_BASE_URL + Contants.BACKDROP_IMAGE_WIDTH + backdropPath)
            .into(imgView);
    }
}
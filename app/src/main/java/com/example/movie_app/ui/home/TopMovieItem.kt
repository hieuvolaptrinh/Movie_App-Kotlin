package com.example.movie_app.ui.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopMovieItem(
    val imageId: Int,
    val name: String
) : Parcelable
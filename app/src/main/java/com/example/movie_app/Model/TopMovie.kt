package com.example.movie_app.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopMovie(
    val imageId: Int,
    val name: String
) : Parcelable
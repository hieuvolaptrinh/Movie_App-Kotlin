package com.example.movie_app.data.remote

import com.example.movie_app.data.model.movie.TopMovieItemResponse
import retrofit2.http.GET

interface TopMovieApi {

    @GET("981ecab0-84f2-43c9-9a4a-6b184708e011")
    suspend fun getPopularMovies(): List<TopMovieItemResponse>


    @GET("9909d92e-a6ef-4ec4-b8a4-f06b0b4bcca6")
    suspend fun getTodayMovies(): List<TopMovieItemResponse>
}
package com.example.movie_app.data.repository

import com.example.movie_app.data.model.movie.TopMovieItemResponse
import com.example.movie_app.data.remote.TopMovieApi
import javax.inject.Inject

class TopMovieRepository @Inject constructor(private val api: TopMovieApi) {
    suspend fun getPopularMovies(): Result<List<TopMovieItemResponse>> {
        return try {
            Result.success(api.getPopularMovies())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTodayMovies(): Result<List<TopMovieItemResponse>> {
        return try {
            return Result.success(api.getTodayMovies())
        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}
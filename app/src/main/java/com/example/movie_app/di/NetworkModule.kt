package com.example.movie_app.di

import com.example.movie_app.data.remote.AuthApi
import com.example.movie_app.data.remote.RetrofitClient
import com.example.movie_app.data.remote.TopMovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



//    Dạy Hilt cách tạo AuthApi
    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return RetrofitClient.authApi
    }

    @Provides
    @Singleton
    fun provideTopMovieApi(): TopMovieApi {
        return RetrofitClient.topMovieApi
    }
}
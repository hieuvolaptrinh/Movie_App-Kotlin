package com.example.movie_app.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//giống như singleton để tạo instance của retrofit, tránh việc tạo nhiều
object RetrofitClient {
    private const val BASE_URL = "https://mocki.io/v1/"

//    https://mocki.io/v1/981ecab0-84f2-43c9-9a4a-6b184708e011   => popular
//    https://mocki.io/v1/9909d92e-a6ef-4ec4-b8a4-f06b0b4bcca6  ==>  today

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    val authApi: AuthApi = retrofit.create(AuthApi::class.java)
    val topMovieApi: TopMovieApi = retrofit.create(TopMovieApi::class.java)
}
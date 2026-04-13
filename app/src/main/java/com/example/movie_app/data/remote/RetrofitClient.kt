package com.example.movie_app.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//giống như singleton để tạo instance của retrofit, tránh việc tạo nhiều
object RetrofitClient {
    private const val BASE_URL ="https://dummyjson.com/"

    private val retrofit=
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

//    tương tự nhiều API thì cứ viết như thế
    val authApi: AuthApi = retrofit.create(AuthApi::class.java)

//    val userApi: UserApi = retrofit.create(UserApi::class.java)
}
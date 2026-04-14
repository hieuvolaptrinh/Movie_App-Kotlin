package com.example.movie_app.data.remote

import com.example.movie_app.data.model.auth.LoginRequest
import com.example.movie_app.data.model.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login( // suspend giống như async, nó sẽ chạy trong background thread và không block main thread
        @Body request: LoginRequest
    ): LoginResponse

}
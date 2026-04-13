package com.example.movie_app.data.repository

import com.example.movie_app.data.model.LoginRequest
import com.example.movie_app.data.model.LoginResponse
import com.example.movie_app.data.remote.AuthApi

class AuthRepository (private val api: AuthApi){
    suspend fun login(username:String, password :String ): Result<LoginResponse> {
        return try {
            val request = LoginRequest(username = username, password = password)
            val response = api.login(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
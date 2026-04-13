package com.example.movie_app.data.model

//example for call api
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val id: String,
    val username: String,
    val email: String,
    val firstName:String,
    val lastName : String,
    val gender: Boolean, // true is male
    val image: String,
){
    override fun toString(): String {
        return "LoginResponse(accessToken='$accessToken', refreshToken='$refreshToken', id='$id', username='$username', email='$email', firstName='$firstName', lastName='$lastName', gender=$gender, image='$image')"
    }
}
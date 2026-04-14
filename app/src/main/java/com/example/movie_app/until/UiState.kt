package com.example.movie_app.until

sealed class UiState<out T> {
    object Idle : UiState<Nothing>() // trạng thái ban đầu, chưa có gì xảy ra
    object Loading : UiState<Nothing>() // trạng thái đang loading, khi người dùng nhấn nút login
    data class Success<T>(val data: T) : UiState<T>() // trạng thái login thành công, có thể chứa dữ liệu token

    data class Error(val message: String) : UiState<Nothing>() // trạng thái login thất bại, có thể chứa dữ liệu lỗi
}
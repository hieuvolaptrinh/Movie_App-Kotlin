package com.example.movie_app.ui.login

import com.example.movie_app.data.model.auth.LoginResponse

//sealed class : lớp niêm phong => nghĩa là bộ danh sách có thể xảy ra
// giống như với enum nhưng nó có thể chứa dữ liệu và có thể có nhiều trường hợp hơn là enum
// giống như là state trong flutter, nó có thể là loading, success, error, v.v... để quản lý trạng thái của UI
sealed class LoginState {
    object Idle : LoginState() // trạng thái ban đầu, chưa có gì xảy ra
    object Loading : LoginState() // trạng thái đang loading, khi người dùng nhấn nút login
    data class Success(val data: LoginResponse) :
        LoginState() // trạng thái login thành công, có thể chứa dữ liệu token

    data class Error(val mesage: String) :
        LoginState() // trạng thái login thất bại, có thể chứa dữ liệu lỗi

}
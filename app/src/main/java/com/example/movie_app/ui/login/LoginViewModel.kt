package com.example.movie_app.ui.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_app.data.repository.AuthRepository
import kotlinx.coroutines.launch

//để khỏi phải tạo factory để cung cấp repository cho view model, chúng ta có thể sử dụng Hilt để inject repository vào view model
class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    //    _ dùng nội bộ trong view model để cập nhật trạng thái, còn state thì dùng để expose ra ngoài cho UI quan sát
//    tránh việc ui thay đổi trực tiếp trạng thái của view model, mà chỉ có view model mới có thể thay đổi nó, giúp bảo vệ tính toàn vẹn của dữ liệu và tránh lỗi không mong muốn
    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState> = _state

    fun login(username: String, password: String) {
//        viewModelScope : android sẽ tự dọn viewmodel khi bị hủy hoặc người dùng ko còn ở đó nữa
        viewModelScope.launch {
            _state.value = LoginState.Loading

            // Gọi repo và nhận về Result
            val result = repository.login(username, password)

            // Cách viết "sạch" nhất trong Kotlin
            result.onSuccess { response ->
                // response ở đây chính là đối tượng LoginResponse
                _state.value = LoginState.Success(data = response)

            }.onFailure { exception ->
                _state.value = LoginState.Error(exception.message ?: "Lỗi đăng nhập")
            }
        }
    }
}
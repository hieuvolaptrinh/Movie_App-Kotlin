package com.example.movie_app.ui.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_app.data.model.auth.LoginResponse
import com.example.movie_app.data.repository.AuthRepository
import com.example.movie_app.until.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    //    _ dùng nội bộ trong view model để cập nhật trạng thái, còn state thì dùng để expose ra ngoài cho UI quan sát
//    tránh việc ui thay đổi trực tiếp trạng thái của view model, mà chỉ có view model mới có thể thay đổi nó, giúp bảo vệ tính toàn vẹn của dữ liệu và tránh lỗi không mong muốn
    private val _state = MutableStateFlow<UiState<LoginResponse>>(UiState.Idle)
    val state: StateFlow<UiState<LoginResponse>> = _state

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val result = repository.login(username, password)

                result.onSuccess { response ->
                    _state.value = UiState.Success(response)
                }.onFailure { exception ->
                    _state.value = UiState.Error(
                        exception.message ?: "Lỗi đăng nhập"
                    )
                }
            } catch (e: Exception) {
                // fallback nếu repo chưa handle hết exception
                _state.value = UiState.Error(
                    e.message ?: "Unknown error"
                )
            }
        }
    }
}
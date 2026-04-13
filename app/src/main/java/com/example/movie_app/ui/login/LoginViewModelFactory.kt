package com.example.movie_app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movie_app.data.repository.AuthRepository
//nếu dùng anotation @HiltViewModel thì không cần tạo factory nữa, vì Hilt sẽ tự động tạo factory cho chúng ta, nhưng nếu không dùng Hilt thì chúng ta cần tạo factory để cung cấp repository cho view model
//dùng để tạo instance của LoginViewModel, vì LoginViewModel có constructor nhận tham số là AuthRepository, nên chúng ta cần một factory để cung cấp AuthRepository khi tạo LoginViewModel
class LoginViewModelFactory(private val repository: AuthRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}
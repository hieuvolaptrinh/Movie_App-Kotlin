package com.example.movie_app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_app.data.model.movie.TopMovieItemResponse
import com.example.movie_app.data.repository.TopMovieRepository
import com.example.movie_app.until.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopMovieViewModel @Inject constructor(
    private val repository: TopMovieRepository
) : ViewModel() {
    private val _todayState = MutableStateFlow<UiState<List<TopMovieItemResponse>>>(UiState.Idle)
    val todayState: StateFlow<UiState<List<TopMovieItemResponse>>> = _todayState

    private val _popularState = MutableStateFlow<UiState<List<TopMovieItemResponse>>>(UiState.Idle)
    val popularState: StateFlow<UiState<List<TopMovieItemResponse>>> = _popularState

    fun getTodayMovies() {
        viewModelScope.launch {
            _todayState.value = UiState.Loading
            try {
                val result = repository.getTodayMovies()
                result.onSuccess { movies ->
                    _todayState.value = UiState.Success(movies)
                }.onFailure { exception ->
                    _todayState.value = UiState.Error(exception.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _todayState.value = UiState.Error(e.message ?: "Unknown error")
            }

        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            _popularState.value = UiState.Loading
            try {
                val result = repository.getPopularMovies()
                result.onSuccess { movies ->
                    _popularState.value = UiState.Success(movies)
                }.onFailure { exception ->
                    _popularState.value = UiState.Error(exception.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _popularState.value = UiState.Error(e.message ?: "Unknown error")
            }

        }
    }

}
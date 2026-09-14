package com.example.androidsprintcomposeapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsprintcomposeapp.data.Joke
import com.example.androidsprintcomposeapp.repository.JokeRepository
import kotlinx.coroutines.launch

class JokeViewModel : ViewModel() {
    data class JokeUiState(
        val isLoading: Boolean = false,
        val joke: Joke? = null,
        val error: String? = null
    )

    private val repository = JokeRepository()

    var uiState by mutableStateOf(value = JokeUiState())
        private set

    fun loadJoke() {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                error = null
            )
            try {
                val result = repository.getJoke()
                uiState = uiState.copy(
                    isLoading = false,
                    joke = result,
                    error = null
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = "No jokes today!"
                )
            }
        }
    }
}
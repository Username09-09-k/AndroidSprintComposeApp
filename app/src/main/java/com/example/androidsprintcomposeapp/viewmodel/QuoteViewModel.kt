package com.example.androidsprintcomposeapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsprintcomposeapp.data.Quote
import com.example.androidsprintcomposeapp.repository.QuoteRepository
import kotlinx.coroutines.launch

class QuoteViewModel : ViewModel() {
    data class QuoteUiState(
        val isLoading: Boolean = false,
        val quote: Quote? = null,
        val error: String? = null
    )
    private val repository = QuoteRepository()

    var uiState by mutableStateOf(value = QuoteUiState())
        private set

    fun loadQuote() {
        viewModelScope.launch {

            uiState = uiState.copy(isLoading = true)
            try {
                val result = repository.getQuote().first()

                uiState = uiState.copy(
                    isLoading = false,
                    quote = result
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = "Can't remember"
                )
            }
        }
    }
}
package com.example.androidsprintcomposeapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import com.example.androidsprintcomposeapp.data.Video
import com.example.androidsprintcomposeapp.repository.VideoRepository
import kotlinx.coroutines.launch

class VideoViewModel(application: Application) : AndroidViewModel(application) {

    data class VideoUiState(
        val isLoading: Boolean = false,
        val video: Video? = null,
        val error: String? = null
    )

    private val repository = VideoRepository()
    var uiState by mutableStateOf(value = VideoUiState())
        private set

    val exoPlayer = ExoPlayer.Builder(getApplication()).build()

    var isFullscreen by mutableStateOf(false)
        private set

    fun updateFullscreen(value: Boolean) {
        if (isFullscreen != value) {
            isFullscreen = value
        }
    }

    fun loadVideo() {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                error = null
            )
            try {
                val resultVideo = repository.getVideo()
                uiState = uiState.copy(
                    isLoading = false,
                    video = resultVideo,
                    error = null
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = "Unfortunately, no connection to backend\n" +
                            "Please check connection"
                )
            }
        }
    }

    @SuppressLint("EmptySuperCall")
    override fun onCleared() {
        exoPlayer.release()
        super.onCleared()
    }
}

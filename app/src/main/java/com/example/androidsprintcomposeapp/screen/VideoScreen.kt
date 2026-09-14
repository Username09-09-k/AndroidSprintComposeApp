package com.example.androidsprintcomposeapp.screen

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.example.androidsprintcomposeapp.R
import com.example.androidsprintcomposeapp.ui.theme.AndroidSprintComposeAppTheme
import com.example.androidsprintcomposeapp.ui.theme.primaryText
import com.example.androidsprintcomposeapp.viewmodel.VideoViewModel

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoScreen(
    viewModel: VideoViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val state = viewModel.uiState
    val isFullscreen = viewModel.isFullscreen

    LaunchedEffect(Unit) {
        viewModel.loadVideo()
    }
    AndroidSprintComposeAppTheme {
        Scaffold(
            topBar = {
                if (!isFullscreen) {
                    TopAppBar(
                        title = {
                            Text("Video")
                        },
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Go Back"
                                )
                            }
                        }
                    )
                }
            },
            content = { innerPadding: PaddingValues ->
                Box(
                    modifier = Modifier
                        .then(
                            if (isFullscreen) {
                                Modifier
                            } else {
                                Modifier.padding(innerPadding)
                            }
                        )
                        .fillMaxSize()
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        state.isLoading -> {
                            CircularProgressIndicator(color = Color.White)
                        }

                        state.error != null -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = state.error,
                                    color = Color.White,
                                    fontSize = 18.sp,
                                )
                                Spacer(modifier = Modifier.height(15.dp))
                                Button(
                                    onClick = {
                                        viewModel.loadVideo() // кнопка повтора видео
                                    },
                                    modifier = Modifier.width(250.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primaryText, // Back color
                                    ),
                                )
                                {
                                    Text(
                                        text = "Repeat",
                                        fontFamily = FontFamily(
                                            Font(R.font.montserrat_bold)
                                        ),
                                    )
                                }
                            }
                        }

                        state.video != null -> {
                            VideoPlayer(
                                url = state.video.url
                            )
                        }
                    }
                }
            }
        )
    }
}

@SuppressLint("SourceLockedOrientationActivity")
@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    url: String,
    viewModel: VideoViewModel = viewModel()
) {
    val exoPlayer = viewModel.exoPlayer
    val isFullscreen = viewModel.isFullscreen

    var isBuffering by remember {
        mutableStateOf(exoPlayer.playbackState == Player.STATE_BUFFERING)
    }

    val activity = LocalActivity.current

    BackHandler(enabled = isFullscreen) { // перехват экрана если идем обратно из горизонт.вида
        viewModel.updateFullscreen(false)
        activity?.requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    // Слушатель состояния видео
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {

            override fun onPlaybackStateChanged(
                playbackState: Int
            ) {
                isBuffering =
                    playbackState == Player.STATE_BUFFERING

                Log.d(
                    "VideoPlayer",
                    "Playback state: $playbackState"
                )
            }

            override fun onPlayerError(error: PlaybackException) {
                Log.e(
                    "VideoPlayer",
                    "Playback error: ${error.errorCodeName}",
                    error
                )
            }
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                Log.d(
                    "VideoPlayer",
                    "Is playing: $isPlaying, position: ${exoPlayer.currentPosition}"
                )
            }
        }
        exoPlayer.addListener(listener)
        // удаляем слушатель и освобождаем плеер
        onDispose {
            exoPlayer.removeListener(listener)
        }
    }

    LaunchedEffect(url) {
        if (exoPlayer.currentMediaItem == null) {  // сохраняем время видео при перевороте экрана
            exoPlayer.setMediaItem(MediaItem.fromUri(url))
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AndroidView(
            modifier = if (isFullscreen) {
                Modifier.fillMaxSize()
            } else {
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
                    .align(Alignment.Center)
            },
            factory = { context ->
                PlayerView(context)
            },
            update = { view ->
                view.player = exoPlayer
                view.useController = true
                view.controllerAutoShow = true
                view.keepScreenOn = true

                view.setControllerShowTimeoutMs(3000)
                view.setFullscreenButtonState(isFullscreen)

                view.setFullscreenButtonClickListener { fullscreen ->
                    viewModel.updateFullscreen(fullscreen)

                    activity?.requestedOrientation =
                        if (fullscreen) {
                            ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                        } else {
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        }
                }
            }
        )
        if (isBuffering) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
    }
}

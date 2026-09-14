package com.example.androidsprintcomposeapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidsprintcomposeapp.R
import com.example.androidsprintcomposeapp.ui.theme.AndroidSprintComposeAppTheme
import com.example.androidsprintcomposeapp.ui.theme.primaryText
import com.example.androidsprintcomposeapp.viewmodel.JokeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokeScreen(
    viewModel: JokeViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val state = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadJoke()
    }

    AndroidSprintComposeAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Anabolik's joke")
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
            },
            content = { innerPadding: PaddingValues ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(paddingValues = innerPadding)
                        .fillMaxSize()
                ) {
                    when {
                        state.isLoading -> {
                            CircularProgressIndicator()
                        }

                        state.error != null -> {
                            Text(
                                text = "Error: ${state.error}"
                            )
                        }

                        state.joke != null -> {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(all = 16.dp),
                                shape = RoundedCornerShape(size = 16.dp),
                                elevation = CardDefaults.cardElevation(6.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    state.joke.joke?.let {
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                    state.joke.setup?.let {
                                        Spacer(
                                            modifier = Modifier.height(8.dp)
                                        )
                                        Text(text = it, style = MaterialTheme.typography.bodyLarge)
                                    }
                                    state.joke.delivery?.let {
                                        Spacer(
                                            modifier = Modifier.height(8.dp)
                                        )
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Button(
                        onClick =
                            {
                                viewModel.loadJoke()
                            },
                        modifier = Modifier.width(250.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryText, // Background color
                        ),
                    ) {
                        Text(
                            text = "One more joke!",
                            fontFamily = FontFamily(
                                Font(R.font.montserrat_bold)
                            )
                        )
                    }
                }
            }
        )
    }
}

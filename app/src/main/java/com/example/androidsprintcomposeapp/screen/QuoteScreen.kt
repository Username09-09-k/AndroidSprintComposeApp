package com.example.androidsprintcomposeapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidsprintcomposeapp.R
import com.example.androidsprintcomposeapp.ui.theme.AndroidSprintComposeAppTheme
import com.example.androidsprintcomposeapp.ui.theme.primaryText
import com.example.androidsprintcomposeapp.viewmodel.QuoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteScreen(
    viewModel: QuoteViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val state = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadQuote()
    }

    AndroidSprintComposeAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Bestor motivation")
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
                            modifier = Modifier.padding(all = 16.dp)
                        ) {
                            when {
                                state.isLoading -> {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                    Image(
                                        painter = painterResource(id = R.drawable.bestor_thinks),
                                        contentDescription = "Bestor thinks",
                                        modifier = Modifier.size(350.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }

                                state.error != null -> {
                                    Text(
                                        text = "Error: ${state.error}",
                                        Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.bestor_fail),
                                        contentDescription = "Bestor crying",
                                        modifier = Modifier.size(350.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }

                                state.quote != null -> {
                                    Text(
                                        text = state.quote.q,
                                        modifier = Modifier
                                            .padding(all = 5.dp)
                                            .fillMaxWidth(),
                                        fontSize = 16.sp,
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.Center,

                                        )
                                    Text(
                                        text = "Said by " + state.quote.a,
                                        fontSize = 16.sp,
                                        modifier = Modifier
                                            .padding(all = 5.dp)
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.bestor_success),
                                        contentDescription = "Bestor happy",
                                        modifier = Modifier.size(350.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Button(
                        onClick =
                            {
                                viewModel.loadQuote()
                            },
                        modifier = Modifier.width(250.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryText, // Back color
                        ),
                    ) {
                        Text(
                            text = "Bestor, one more please!",
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

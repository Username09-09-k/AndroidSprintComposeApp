package com.example.androidsprintcomposeapp.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.androidsprintcomposeapp.model.CsvReader
import com.example.androidsprintcomposeapp.ui.theme.AndroidSprintComposeAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val allItems = remember {
        CsvReader.loadCsv(context)
    }

    var query by remember { mutableStateOf("") }

    val filteredItems = remember(query, allItems) {
        allItems.filter {
            it.user.contains(query, ignoreCase = true) ||
                    it.title.contains(query, ignoreCase = true)
        }
    }
    AndroidSprintComposeAppTheme {
        Scaffold(
            topBar = {
                Column {
                    TopAppBar(
                        title = {
                            Text("Domain computers")
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
                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = {
                            Text(
                                "Search"
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
                        trailingIcon = {
                            if (query.isNotEmpty()) {
                                IconButton(
                                    onClick = { query = "" }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear text"
                                    )
                                }
                            }
                        }
                    )
                }
            },
            content = { innerPadding: PaddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues = innerPadding)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    LazyColumn {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(all = 5.dp)
                            ) {
                                Text(
                                    text = "Device",
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "User",
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        // Данные
                        items(filteredItems) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(size = 4.dp)
                                    )
                                    .padding(all = 8.dp)
                            ) {
                                Text(
                                    text = item.title,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = item.user,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

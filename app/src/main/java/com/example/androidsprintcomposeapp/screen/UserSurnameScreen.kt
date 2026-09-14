package com.example.androidsprintcomposeapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.androidsprintcomposeapp.model.UserSurnameCsvReader
import com.example.androidsprintcomposeapp.ui.theme.AndroidSprintComposeAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSurnameScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val allItems = remember {
        UserSurnameCsvReader.userSurnameLoadCsv(context)
    }

    var query by remember { mutableStateOf("") }

    val filteredItems = remember(query, allItems) {

        // фильтр поиска (имя, фамилия, телефон, мобильный, учётка винды, департамент)
        allItems.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.telephone.contains(query, ignoreCase = true) ||
                    it.mobile.contains(query, ignoreCase = true) ||
                    it.username.contains(query, ignoreCase = true) ||
                    it.department.contains(query, ignoreCase = true)
        }
    }
    AndroidSprintComposeAppTheme {
        Scaffold(
            topBar = {
                Column {
                    TopAppBar(
                        title = { Text("Domain users") },
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
                                text = "Search",
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
                        .padding(innerPadding)
                        .fillMaxSize(),
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        // Данные
                        items(filteredItems) { item ->
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) { }
                            OptionalText(item.name)
                            OptionalText(item.username)
                            OptionalText(item.title)
                            OptionalText(item.telephone)
                            OptionalText(item.mobile)
                            OptionalText(item.email)
                            OptionalText(item.department)
                            HorizontalDivider(
                                modifier = Modifier.padding(
                                    top = 10.dp,
                                    bottom = 10.dp
                                )
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun OptionalText(value: String) {
    if (value.isNotBlank()) {
        Text(text = value)
    }
}
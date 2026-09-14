package com.example.androidsprintcomposeapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androidsprintcomposeapp.R

@Composable
fun ThemedImage() {
    val isDark = isSystemInDarkTheme()

    val imageRes = if (isDark) {
        R.drawable.qarmet_logo_white
    } else {
        R.drawable.qarmet_logo_dark
    }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "Company logo",
        modifier = Modifier
            .size(260.dp)
    )
}
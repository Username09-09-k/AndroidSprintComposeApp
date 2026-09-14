package com.example.androidsprintcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.androidsprintcomposeapp.navigation.AppNavHost
import com.example.androidsprintcomposeapp.ui.theme.AndroidSprintComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidSprintComposeAppTheme {
                val navController = rememberNavController()
                AppNavHost(navHostController = navController)
            }
        }
    }
}

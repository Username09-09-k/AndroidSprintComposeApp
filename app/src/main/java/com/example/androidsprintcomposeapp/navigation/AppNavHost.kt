package com.example.androidsprintcomposeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidsprintcomposeapp.screen.HomeScreen
import com.example.androidsprintcomposeapp.screen.InfoScreen
import com.example.androidsprintcomposeapp.screen.IntroScreen
import com.example.androidsprintcomposeapp.screen.JokeScreen
import com.example.androidsprintcomposeapp.screen.QuoteScreen
import com.example.androidsprintcomposeapp.screen.UserSurnameScreen
import com.example.androidsprintcomposeapp.screen.VideoScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Destination.Intro.route,
    ) {
        composable(route = Destination.Home.route) {
            HomeScreen(
                onComputerDataClick = {
                    navHostController.navigate(Destination.Computer.route)
                },
                onUserDataClick = {
                    navHostController.navigate(Destination.UserInfo.route)
                },
                onQuoteClick = {
                    navHostController.navigate(Destination.Quote.route)
                },
                onJokeClick = {
                    navHostController.navigate(Destination.Joke.route)
                },
                onVideoClick = {
                    navHostController.navigate(Destination.Video.route)
                }
            )
        }
        composable(route = Destination.Computer.route) {
            InfoScreen(
                onBackClick = {
                    navHostController.navigateUp()
                }
            )
        }
        composable(Destination.UserInfo.route) {
            UserSurnameScreen(
                onBackClick = {
                    navHostController.navigateUp()
                }
            )
        }
        composable(route = Destination.Intro.route) {
            IntroScreen(
                onStartClick = {
                    navHostController.navigate(Destination.Home.route) {
                        popUpTo(Destination.Intro.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Destination.Quote.route) {
            QuoteScreen(
                onBackClick = {
                    navHostController.navigateUp()
                }
            )
        }
        composable(Destination.Joke.route) {
            JokeScreen(
                onBackClick = {
                    navHostController.navigateUp()
                }
            )
        }
        composable(Destination.Video.route){
            VideoScreen(
                onBackClick = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}

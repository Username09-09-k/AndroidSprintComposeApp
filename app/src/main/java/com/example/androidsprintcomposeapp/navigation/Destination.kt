package com.example.androidsprintcomposeapp.navigation

sealed class Destination(val route: String) {
    data object Home : Destination(ROUTE_HOME)
    data object Computer : Destination(ROUTE_COMPUTER)
    data object UserInfo : Destination(ROUTE_USER)
    data object Intro : Destination(ROUTE_INTRO)
    data object Quote : Destination(ROUTE_QUOTE)
    data object Joke : Destination(ROUTE_JOKE)
    data object Video : Destination(ROUTE_VIDEO)

    companion object {
        private const val ROUTE_HOME = "route_home"
        private const val ROUTE_COMPUTER = "route_computer_info"
        private const val ROUTE_USER = "route_user_info"
        private const val ROUTE_INTRO = "route_intro"
        private const val ROUTE_QUOTE = "route_quote"
        private const val ROUTE_JOKE = "route_joke"
        private const val ROUTE_VIDEO = "route_video"
    }
}
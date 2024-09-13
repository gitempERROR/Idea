package com.example.idea.domain.navigation

sealed class Routes(val route: String) {
    data object Splash : Routes("splash")
    data object Login : Routes("login")
}
package com.example.idea.view.MainActivity.navigation

sealed class Routes(val route: String) {
    data object NewIdea : Routes("newIdea")
    data object Splash : Routes("splash")
    data object Login : Routes("login")
    data object Main : Routes("main")
    data object Idea : Routes("idea/{ideaName}/{ideaDesc}/{ideaStatus}/{ideaId}")
}
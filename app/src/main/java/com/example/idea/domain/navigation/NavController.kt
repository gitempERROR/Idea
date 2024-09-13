package com.example.idea.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.idea.view.Login
import com.example.idea.view.Splash

@Composable
fun NavController(controller: NavHostController) {
    NavHost(
        navController = controller,
        startDestination = Routes.Splash.route
    ) {
        composable(Routes.Splash.route){
            Splash(controller)
        }
        composable(Routes.Login.route){
            Login(controller)
        }
    }
}
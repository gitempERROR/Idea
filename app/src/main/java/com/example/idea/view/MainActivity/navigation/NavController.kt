package com.example.idea.view.MainActivity.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.idea.view.MainActivity.components.Idea
import com.example.idea.view.MainActivity.components.Login
import com.example.idea.view.MainActivity.components.Splash
import com.example.idea.view.MainActivity.components.Main
import com.example.idea.view.MainActivity.components.NewIdea

@Composable
fun NavController(controller: NavHostController) {
    NavHost(
        navController = controller,
        startDestination = Routes.Splash.route
    ) {
        composable(Routes.NewIdea.route){
            NewIdea(controller)
        }
        composable(Routes.Splash.route){
            Splash(controller)
        }
        composable(Routes.Login.route){
            Login(controller)
        }
        composable(Routes.Main.route){
            Main(controller)
        }
        composable(
            Routes.Idea.route,
            arguments = listOf(
                navArgument("ideaName")   { type = NavType.StringType},
                navArgument("ideaDesc")   { type = NavType.StringType},
                navArgument("ideaStatus") { type = NavType.StringType},
                navArgument("ideaId")     { type = NavType.StringType}
            )
            ) { backStackEntry ->
            val ideaName = backStackEntry.arguments?.getString("ideaName")
            val ideaDesc = backStackEntry.arguments?.getString("ideaDesc")
            val ideaStatus = backStackEntry.arguments?.getString("ideaStatus")!!.replace("'","")
            val ideaId = backStackEntry.arguments?.getString("ideaId")
            Idea(controller, ideaName!!, ideaDesc!!, ideaStatus, ideaId!!)
        }
    }
}
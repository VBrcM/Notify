package com.example.notify.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun Nav(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController,
        startDestination = Screens.AuthRoute.route,
            enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        })
    {
        authGraph(navController)
        dashGraph(navController)
        groupGraph(navController)
    }
}
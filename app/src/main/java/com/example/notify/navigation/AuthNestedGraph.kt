package com.example.notify.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.notify.screens.dashboard.Dashboard
import com.example.notify.screens.authScreen.LoginScreen
import com.example.notify.screens.authScreen.SignupScreen
import com.example.notify.viewModels.LoginViewModel

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        startDestination = Screens.ScreenLoginRoute.route,
        route = Screens.AuthRoute.route
    ) {
        composable(route = Screens.ScreenLoginRoute.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screens.ScreenSignupRoute.route) {
            SignupScreen(navController = navController)
        }
        composable(route = Screens.ScreenDashRoute.route) {
            Dashboard(navController = navController)
        }
    }
}


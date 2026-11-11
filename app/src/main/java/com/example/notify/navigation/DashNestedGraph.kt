package com.example.notify.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.notify.screens.dashboard.AddAlarmScreen
import com.example.notify.screens.dashboard.AlarmInfoScreen
import com.example.notify.screens.dashboard.ProfileScreen
import com.example.notify.screens.group.GroupScreen

fun NavGraphBuilder.dashGraph(navController: NavController){
    navigation(
        startDestination = Screens.ScreenDashRoute.route,
        route = Screens.AppRoute.route
    ) {
        composable(route = Screens.ScreenAddAlarmRoute.route) {
            AddAlarmScreen(navController = navController)
        }
        composable(route = Screens.ScreenGroupRoute.route) {
            GroupScreen(navController = navController)
        }
        composable(route = Screens.ScreenProfileRoute.route){
            ProfileScreen(navController = navController)
        }
        composable(
            route = "${Screens.ScreenAlarmInfoRoute.route}/currentGroupId/{currentGroupId}/alarmId/{alarmId}",
            arguments = listOf(
                navArgument("currentGroupId") { type = NavType.StringType },
                navArgument("alarmId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val currentGroupId = backStackEntry.arguments?.getString("currentGroupId") ?: ""
            val alarmId = backStackEntry.arguments?.getString("alarmId") ?: ""
            AlarmInfoScreen(navController = navController, currentGroupId = currentGroupId, alarmId = alarmId)
        }
    }
}
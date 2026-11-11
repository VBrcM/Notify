package com.example.notify.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.notify.screens.group.CreateGroupScreen
import com.example.notify.screens.group.GroupInfoScreen
import com.example.notify.screens.group.JoinGroupScreen

fun NavGraphBuilder.groupGraph(navController: NavController) {
    navigation(
        startDestination = Screens.ScreenGroupRoute.route,
        route = Screens.GroupRoute.route
    ) {
        composable(route = Screens.ScreenCreateGroupRoute.route) {
            CreateGroupScreen(navController = navController)
        }
        composable(route = Screens.ScreenJoinGroupRoute.route) {
            JoinGroupScreen(navController = navController)
        }
        composable(
            route = "${Screens.ScreenGroupInfoRoute.route}/{groupId}",
            arguments = listOf(navArgument("groupId") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val groupId = navBackStackEntry.arguments?.getString("groupId") ?: ""
            GroupInfoScreen(navController = navController, groupId = groupId)
        }
    }
}
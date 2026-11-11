package com.example.notify.navigation

sealed class Screens (val route: String){
    data object ScreenLoginRoute : Screens(route = "LoginScreen")
    data object ScreenSignupRoute : Screens(route = "SignupScreen")
    data object ScreenDashRoute : Screens(route = "Dashboard")
    data object AuthRoute : Screens(route = "Auth")
    data object AppRoute : Screens(route = "App")
    data object GroupRoute : Screens(route = "Groups")

    data object ScreenGroupRoute : Screens(route = "GroupScreen")
    data object ScreenAddAlarmRoute : Screens(route = "AddAlarmScreen")
    data object ScreenProfileRoute : Screens(route = "ProfileScreen")
    data object ScreenAlarmInfoRoute : Screens(route = "AlarmInfoScreen")

    data object ScreenCreateGroupRoute : Screens(route = "CreateGroupScreen")
    data object ScreenJoinGroupRoute : Screens(route = "JoinGroupScreen")
    data object ScreenGroupInfoRoute : Screens(route = "GroupInfoScreen")
}
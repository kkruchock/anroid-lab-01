package com.example.androidlab01.homework_3.present.navigation

sealed class NavRoutes(val route: String) {

    data object Settings : NavRoutes("settings")

    data object Edit : NavRoutes("edit")

    data object Messages : NavRoutes("messages")
}
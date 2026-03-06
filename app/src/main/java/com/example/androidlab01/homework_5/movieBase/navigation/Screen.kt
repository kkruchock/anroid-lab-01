package com.example.androidlab01.homework_5.movieBase.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Movies : Screen("movies")
    object AddMovie : Screen("add_movie")
    object Profile : Screen("profile")
}
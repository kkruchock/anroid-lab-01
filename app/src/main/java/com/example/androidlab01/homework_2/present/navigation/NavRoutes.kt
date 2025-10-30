package com.example.androidlab01.homework_2.present.navigation

import android.os.Bundle

sealed class NavRoutes(val route: String) {

    data object Auth : NavRoutes("login")
    data object NotesList : NavRoutes("notesList/{${NavKeys.EMAIL}}") {
        fun createRoute(email: String): String {
            return "notesList/$email"
        }

        fun getEmail(arguments: Bundle?): String {
            return arguments?.getString(NavKeys.EMAIL).orEmpty()
        }
    }
    data object NoteAdding : NavRoutes("noteAdding")
}
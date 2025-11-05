package com.example.androidlab01.homework_2.present.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidlab01.homework_2.domain.model.Note
import com.example.androidlab01.homework_2.present.screens.auth.AuthScreen
import com.example.androidlab01.homework_2.present.screens.noteAdding.NoteAddingScreen
import com.example.androidlab01.homework_2.present.screens.notesList.NotesListScreen
import com.example.androidlab01.homework_2.present.viewmodel.NotesViewModel

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    val viewModel: NotesViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Auth.route
    ) {
        composable(NavRoutes.Auth.route) {
            AuthScreen(
                onSuccessAuth = { email ->
                    navController.navigate(
                        NavRoutes.NotesList.createRoute(email)
                    )
                }
            )
        }
        composable(NavRoutes.NotesList.route) {
            val email = NavRoutes.NotesList.getEmail(it.arguments)
            val savedStateHandle = it.savedStateHandle
            savedStateHandle.get<Note>(NavKeys.NEW_NOTE)?.let {
                note -> viewModel.addNote(note)
                savedStateHandle.remove<Note>(NavKeys.NEW_NOTE)
            }
            NotesListScreen(
                email = email,
                notes = viewModel.notes,
                onAddNoteClick = {
                    navController.navigate(NavRoutes.NoteAdding.route)
                }
            )
        }
        composable(NavRoutes.NoteAdding.route) {
            NoteAddingScreen(
                onNoteSavedClick = {
                    viewModel.addNote(it)
                    navController.popBackStack()
                }
            )
        }
    }
}
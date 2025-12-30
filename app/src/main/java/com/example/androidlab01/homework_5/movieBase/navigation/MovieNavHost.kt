package com.example.androidlab01.homework_5.movieBase.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidlab01.homework_5.movieBase.di.AppContainer
import com.example.androidlab01.homework_5.movieBase.ui.auth.LoginScreen
import com.example.androidlab01.homework_5.movieBase.ui.auth.LoginViewModel
import com.example.androidlab01.homework_5.movieBase.ui.auth.RegisterScreen
import com.example.androidlab01.homework_5.movieBase.ui.auth.RegisterViewModel
import com.example.androidlab01.homework_5.movieBase.ui.movies.AddMovieScreen
import com.example.androidlab01.homework_5.movieBase.ui.movies.AddMovieViewModel
import com.example.androidlab01.homework_5.movieBase.ui.movies.MoviesScreen
import com.example.androidlab01.homework_5.movieBase.ui.movies.MoviesViewModel
import com.example.androidlab01.homework_5.movieBase.ui.profile.ProfileScreen
import com.example.androidlab01.homework_5.movieBase.ui.profile.ProfileViewModel

@Composable
fun MovieNavHost(
    navController: NavHostController,
    appContainer: AppContainer,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            val viewModel = remember {
                LoginViewModel(
                    appContainer.userRepository,
                    appContainer.sessionManager,
                    appContainer.validators
                )
            }
            LoginScreen(
                viewModel = viewModel,
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = {
                    navController.navigate(Screen.Movies.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            val viewModel = remember {
                RegisterViewModel(
                    appContainer.userRepository,
                    appContainer.sessionManager,
                    appContainer.validators
                )
            }
            RegisterScreen(
                viewModel = viewModel,
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.navigate(Screen.Movies.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Movies.route) {
            val viewModel = remember {
                MoviesViewModel(appContainer.movieRepository, appContainer.sessionManager)
            }
            MoviesScreen(
                viewModel = viewModel,
                onAddMovieClick = {
                    navController.navigate(Screen.AddMovie.route)
                },
                onProfileClick = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        composable(Screen.AddMovie.route) {
            val viewModel = remember {
                AddMovieViewModel(
                    appContainer.movieRepository,
                    appContainer.sessionManager,
                    appContainer.validators
                )
            }
            AddMovieScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSaveSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Profile.route) {
            val viewModel = remember {
                ProfileViewModel(appContainer.userRepository, appContainer.sessionManager)
            }
            ProfileScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
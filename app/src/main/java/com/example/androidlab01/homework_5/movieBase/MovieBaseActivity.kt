package com.example.androidlab01.homework_5.movieBase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.androidlab01.homework_5.movieBase.di.AppContainer
import com.example.androidlab01.homework_5.movieBase.navigation.MovieNavHost
import com.example.androidlab01.homework_5.movieBase.navigation.Screen
import com.example.androidlab01.ui.theme.AndroidLab01Theme

class MovieBaseActivity : ComponentActivity() {

    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        appContainer = AppContainer(applicationContext)

        //определяем стартовый экран на основе сессии
        val startDestination = if (appContainer.sessionManager.isLoggedIn()) {
            Screen.Movies.route
        } else {
            Screen.Login.route
        }

        setContent {
            AndroidLab01Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MovieNavHost(
                        navController = navController,
                        appContainer = appContainer,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}
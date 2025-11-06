package com.example.androidlab01.homework_3.present.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.androidlab01.homework_3.present.navigation.BottomNavBar
import com.example.androidlab01.homework_3.present.navigation.NavGraph
import com.example.androidlab01.homework_3.present.viewModel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            val appViewModel: AppViewModel = viewModel()

            Scaffold(
                bottomBar = {
                    BottomNavBar(navController)
                }
            ) { innerPadding ->

                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                ) {
                    NavGraph(
                        navController = navController,
                        appViewModel = appViewModel
                    )
                }
            }
        }
    }
}
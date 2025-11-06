package com.example.androidlab01.homework_3.present.navigation;

import androidx.compose.runtime.Composable;
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidlab01.homework_3.domain.utils.NotificationHandler
import com.example.androidlab01.homework_3.domain.utils.ResManager
import com.example.androidlab01.homework_3.present.screens.edit.EditScreen
import com.example.androidlab01.homework_3.present.screens.messages.MessagesScreen
import com.example.androidlab01.homework_3.present.screens.settings.SettingsScreen
import com.example.androidlab01.homework_3.present.viewModel.AppViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    appViewModel: AppViewModel
) {

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Settings.route
    ) {
        composable(NavRoutes.Settings.route) {
            SettingsScreen(appViewModel)
        }
        composable(NavRoutes.Edit.route) {
            val ctx = LocalContext.current
            val resManager = remember { ResManager(ctx) }
            val handler = remember(ctx) { NotificationHandler(ctx, resManager) }
            EditScreen(ctx, resManager, handler)
        }
        composable(NavRoutes.Messages.route) {
            MessagesScreen()
        }
    }
}
package com.example.androidlab01.homework_3.present.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.androidlab01.R

data class BottomNavItem(
    val route: String,
    @StringRes val titleRes: Int,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(NavRoutes.Settings.route, R.string.bottom_nav_settings, Icons.Filled.Settings),
    BottomNavItem(NavRoutes.Edit.route, R.string.bottom_nav_edit, Icons.Filled.Edit),
    BottomNavItem(NavRoutes.Messages.route, R.string.bottom_nav_messages, Icons.Filled.Email)
)
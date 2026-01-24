package com.tonetutor.app.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.material3.*
import com.tonetutor.app.ui.screens.*

enum class Screen { HOME, SETTINGS }

@Composable
fun NavigationRoot() {
    var currentScreen by remember { mutableStateOf(Screen.HOME) }

    when (currentScreen) {
        Screen.HOME ->
            TutorDashboard { currentScreen = Screen.SETTINGS }

        Screen.SETTINGS ->
            SettingsScreen { currentScreen = Screen.HOME }
    }
}

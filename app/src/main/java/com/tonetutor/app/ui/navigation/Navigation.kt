package com.tonetutor.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tonetutor.app.ui.screens.SettingsScreen
import com.tonetutor.app.ui.screens.TutorDashboard

enum class Screen { HOME, SETTINGS }

@Composable
fun NavigationRoot() {
    var currentScreen by remember { mutableStateOf(Screen.HOME) }

    when (currentScreen) {
        Screen.HOME -> TutorDashboard(
            onOpenSettings = { currentScreen = Screen.SETTINGS }
        )

        Screen.SETTINGS -> SettingsScreen(
            onBack = { currentScreen = Screen.HOME }
        )
    }
}

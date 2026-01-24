
package com.tonetutor.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    background = AppBackgroundColor,
    surface = AppBackgroundColor,
    onBackground = AccentGold,
    onSurface = AccentGold
)

@Composable
fun ToneTutorTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        content = content
    )
}

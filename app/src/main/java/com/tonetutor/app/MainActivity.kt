
package com.tonetutor.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tonetutor.app.ui.theme.ToneTutorTheme
import com.tonetutor.app.ui.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToneTutorTheme {
                MainScreen()
            }
        }
    }
}

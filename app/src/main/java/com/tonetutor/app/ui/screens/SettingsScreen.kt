package com.tonetutor.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api // Import the annotation

@OptIn(ExperimentalMaterial3Api::class) // Add this annotation
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        // For a standard back arrow, it's better to use Icons.Auto.ArrowBack
                        // but for simplicity, your original code is kept.
                        Text("←")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Connect Device (placeholder)")
            Text("FX App Selection (placeholder)")
            Text("Latency / Buffer Size (placeholder)")
            Text("Audio API: AAudio / OpenSLES (placeholder)")
        }
    }
}

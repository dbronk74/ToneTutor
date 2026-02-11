    package com.tonetutor.app.ui.components // It's good practice to put reusable components in their own sub-package

    import androidx.compose.material3.Button
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier

    @Composable
    fun GlassButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        text: String
    ) {
        // This is a placeholder. You would add your custom glassmorphism styling here.
        Button(onClick = onClick, modifier = modifier) {
            Text(text = text)
        }
    }
    
package com.tonetutor.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize // Make sure this is imported
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.rotate
import com.tonetutor.app.ui.theme.AccentGold

enum class OrbState {
    IDLE,
    RECORDING,
    ANALYZING
}

@Composable
fun AiOracle(
    state: OrbState = OrbState.IDLE,
    isSpeaking: Boolean = false,  // Pass true when TTS is speaking results
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")

    // Base idle pulse
    val baseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "baseScale"
    )

    // Stronger pulse when speaking (faster/brighter)
    val speakPulse by if (isSpeaking) infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "speakPulse"
    ) else animateFloatAsState(1f, label = "speakPulseOff")

    // Alpha/glow fade
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    val glowColor = if (isSpeaking || state == OrbState.ANALYZING) AccentGold else AccentGold.copy(alpha = 0.6f)

    Box(modifier = modifier.size(200.dp)) {
        // Outer ripple glow (stronger when speaking)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .scale(baseScale * speakPulse)
                .alpha(alpha * 0.8f)
                .blur(40.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(glowColor, Color.Transparent)
                    ),
                    CircleShape
                )
        )

        // Core orb
        Box(
            modifier = Modifier
                .fillMaxSize(0.6f)
                .align(Alignment.Center)
                .scale(speakPulse.coerceAtLeast(1f))
                .background(glowColor.copy(alpha = 0.9f), CircleShape)
                .blur(15.dp)
        )

        // Subtle musical "strings" radiating (visible in RECORDING/ANALYZING/speaking)
        if (state == OrbState.RECORDING || state == OrbState.ANALYZING || isSpeaking) {
            Canvas(modifier = Modifier.fillMaxSize()) { // Correct way to use the modifier
                val center = Offset(size.width / 2, size.height / 2)
                repeat(6) { i ->
                    rotate(degrees = i * 60f) {
                        drawLine(
                            color = glowColor.copy(alpha = 0.5f),
                            start = center.copy(y = center.y - size.height / 3),
                            end = center.copy(y = center.y + size.height / 3),
                            strokeWidth = 6f
                        )
                    }
                }
            }
        }
    }
}

package com.tonetutor.app.ui.screens

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tonetutor.app.ui.components.PulsingGuitar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TutorDashboard(onOpenSettings: () -> Unit) {
    var aiResponse by remember { mutableStateOf("") }

    val context = LocalContext.current
    val tts = remember { TextToSpeech(context) {} }
    val scope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        onDispose { tts.shutdown() }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            // Base blue fill ALWAYS covers entire screen
            .background(Color(0xFF0B2A5A))
            // Single cohesive background stack (radial glow + vertical vignette)
            .drawWithCache {
                val c = Offset(size.width / 2f, size.height / 2f)

                val radial = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1DA7FF).copy(alpha = 0.22f),
                        Color(0xFF0B2A5A).copy(alpha = 0.00f)
                    ),
                    center = c,
                    radius = size.minDimension * 0.95f
                )

                val vertical = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF01040A).copy(alpha = 0.85f),
                        Color.Transparent,
                        Color(0xFF01040A).copy(alpha = 0.85f)
                    ),
                    startY = 0f,
                    endY = size.height
                )

                onDrawBehind {
                    // base already painted by .background(...)
                    drawRect(radial)
                    drawRect(vertical)
                }
            }
    ) {
        // Optional extra glow (keep if you like the aura; reduce alpha if too strong)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(520.dp)
                    .blur(140.dp)
                    .background(Color(0xFF00BFFF).copy(alpha = 0.18f), CircleShape)
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {

            // TOP ROW (glass icon pucks + labels below)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(top = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlassPuckWithLabel(
                    iconId = PuckIcon.Chat,
                    label = "Chat",
                    onClick = { /* TODO */ }
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    GlassPuckWithLabel(
                        iconId = PuckIcon.IRig,
                        label = "iRig",
                        onClick = { /* TODO */ }
                    )
                    GlassPuckWithLabel(
                        iconId = PuckIcon.Settings,
                        label = "Set",
                        onClick = onOpenSettings
                    )
                }
            }

            // HERO (guitar larger)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                PulsingGuitar(
                    modifier = Modifier
                        .fillMaxHeight(1.0f)
                        .fillMaxWidth()
                        .scale(1.22f)
                        .offset(y = (-26).dp)
                )
            }

            // BOTTOM CONTROL RING — 5 BUTTONS ONLY
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 70.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlassPuckWithLabel(iconId = PuckIcon.Setup, label = "Setup")
                GlassPuckWithLabel(
                    iconId = PuckIcon.Analyze,
                    label = "Analyze",
                    onClick = {
                        aiResponse = "Analyzing tone… (stub)"
                        tts.speak(aiResponse, TextToSpeech.QUEUE_FLUSH, null, null)
                        scope.launch { delay(1200) }
                    }
                )
                GlassPuckWithLabel(iconId = PuckIcon.Record, label = "Record")
                GlassPuckWithLabel(iconId = PuckIcon.Tempo, label = "Tempo")
                GlassPuckWithLabel(iconId = PuckIcon.Library, label = "Library")
            }
        }

        // Optional AI response overlay
        if (aiResponse.isNotBlank()) {
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 110.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xAA000000))
            ) {
                Text(
                    text = aiResponse,
                    color = Color.White,
                    modifier = Modifier.padding(14.dp)
                )
            }
        }

        // Text nav row only (Studio / Recordings / Progress)
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.22f))
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Studio", color = Color(0xFFFFC107), fontWeight = FontWeight.SemiBold)
            Text("Recordings", color = Color.LightGray)
            Text("Progress", color = Color.LightGray)
        }
    }
}

private enum class PuckIcon {
    Chat, IRig, Settings, Setup, Analyze, Record, Tempo, Library
}

@Composable
private fun GlassPuckWithLabel(
    iconId: PuckIcon,
    label: String,
    puckSize: Dp = 62.dp,
    onClick: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.widthIn(min = puckSize)
    ) {
        val puckModifier = Modifier
            .size(puckSize)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.10f))
            .border(1.dp, Color.White.copy(alpha = 0.22f), CircleShape)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)

        Box(
            modifier = puckModifier,
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .blur(12.dp)
                    .background(Color.White.copy(alpha = 0.06f), CircleShape)
            )

            PuckIconGlyph(iconId)
        }

        Spacer(Modifier.height(6.dp))

        Text(
            text = label,
            color = Color.White.copy(alpha = 0.92f),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Composable
private fun PuckIconGlyph(iconId: PuckIcon) {
    val glyph = when (iconId) {
        PuckIcon.Chat -> "◔"
        PuckIcon.IRig -> "⌁"
        PuckIcon.Settings -> "⦿"
        PuckIcon.Setup -> "◈"
        PuckIcon.Analyze -> "▵"
        PuckIcon.Record -> "●"
        PuckIcon.Tempo -> "⏱"
        PuckIcon.Library -> "♫"
    }

    Text(
        text = glyph,
        color = Color.White,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.alpha(0.95f)
    )
}

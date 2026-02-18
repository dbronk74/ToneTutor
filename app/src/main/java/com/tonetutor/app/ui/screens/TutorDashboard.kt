package com.tonetutor.app.ui.screens

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF020617),
                        Color(0xFF0B2A5A),
                        Color(0xFF020617)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ===== TOP BUTTONS =====

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CircleButton("iRig")
                CircleButton("Chat")
                CircleButton("Settings") { onOpenSettings() }
            }

            // ===== HERO GUITAR =====
            // anchored top, expanded downward

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp), // enlarged downward
                contentAlignment = Alignment.TopCenter
            ) {
                PulsingGuitar(
                    modifier = Modifier.fillMaxHeight()
                )
            }

            // ===== MAIN CONTROL ROW =====

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                CircleButton("Setup")
                CircleButton("Analyze")

                RecordButton {
                    aiResponse = "Great tone! Keep practicing phrasing."
                    tts.speak(aiResponse, TextToSpeech.QUEUE_FLUSH, null, null)

                    scope.launch {
                        delay(3500)
                        aiResponse = ""
                    }
                }

                CircleButton("Tempo")
                CircleButton("Library")
            }

            // ===== SECONDARY BUTTON ROW =====

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CircleButton("Studio")
                CircleButton("Recordings")
                CircleButton("Progress")
            }

            // ===== AI RESPONSE =====

            if (aiResponse.isNotEmpty()) {

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xAA000000)
                    )
                ) {
                    Text(
                        aiResponse,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

// ===== BUTTON COMPONENTS =====

@Composable
fun CircleButton(
    label: String,
    onClick: (() -> Unit)? = null
) {
    Surface(
        shape = CircleShape,
        color = Color(0x22000000),
        modifier = Modifier.size(64.dp),
        onClick = { onClick?.invoke() }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                label,
                color = Color.White,
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun RecordButton(onClick: () -> Unit) {

    FloatingActionButton(
        onClick = onClick,
        containerColor = Color(0xFFFFA726),
        modifier = Modifier.size(72.dp)
    ) {
        Text("REC", color = Color.Black)
    }
}

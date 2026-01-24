package com.tonetutor.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tonetutor.app.ui.components.AiOracle
import com.tonetutor.app.ui.components.OrbState
import android.speech.tts.TextToSpeech
import android.content.Context
import androidx.compose.ui.platform.LocalContext

@Composable
fun TutorDashboard(onOpenSettings: () -> Unit) {
    var prompt by remember { mutableStateOf("") }
    var aiResponse by remember { mutableStateOf("") }
    var orbState by remember { mutableStateOf(OrbState.IDLE) }
    var isSpeaking by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val tts = remember { TextToSpeech(context) { } }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "ToneTutor AI",
                color = Color(0xFFFFC107),
                fontSize = 32.sp,
                modifier = Modifier.padding(top = 32.dp)
            )

            Spacer(Modifier.height(32.dp))

            AiOracle(
                state = orbState,
                isSpeaking = isSpeaking,
                modifier = Modifier.size(200.dp)
            )

            Spacer(Modifier.height(32.dp))

            BasicTextField(
                value = prompt,
                onValueChange = { prompt = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF222222))
                    .padding(16.dp),
                decorationBox = { innerTextField ->
                    if (prompt.isEmpty()) {
                        Text("Describe your playing or goal...", color = Color.Gray)
                    }
                    innerTextField()
                }
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    orbState = OrbState.ANALYZING
                    // Simulate AI response (replace with real analysis later)
                    aiResponse = "Great tone! Work on alternate picking in pentatonic scales. Try this routine: 30 min scales, 20 min bends."
                    isSpeaking = true
                    tts.speak(aiResponse, TextToSpeech.QUEUE_FLUSH, null, null)
                    // Reset after "speaking" (add real TTS callback for accuracy)
                    kotlinx.coroutines.GlobalScope.launch {
                        kotlinx.coroutines.delay(5000) // Placeholder delay
                        isSpeaking = false
                        orbState = OrbState.IDLE
                    }
                },
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Analyze Playing")
            }

            Spacer(Modifier.height(32.dp))

            if (aiResponse.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Brush.verticalGradient(listOf(Color(0xFF333333), Color.Black)))
                        .padding(14.dp)
                ) {
                    Column {
                        Text(aiResponse, color = Color.White)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Switch(
                                checked = true, // Placeholder
                                onCheckedChange = { /* Toggle speak */ }
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Speak response", color = Color.LightGray)
                        }
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            TextButton(onClick = onOpenSettings) {
                Text("Settings", color = Color.Gray)
            }
        }
    }
}
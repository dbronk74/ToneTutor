package com.tonetutor.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay
import com.tonetutor.app.R

@Composable
fun PulsingGuitar(modifier: Modifier = Modifier) {

    val frames = listOf(
        R.drawable.tonetutor_guitar_lighter_1_drk,
        R.drawable.tonetutor_guitar_lighter_2_lgter,
        R.drawable.tonetutor_guitar_lighter_3_lighter,
        R.drawable.tonetutor_guitar_lighter_4_lightest
    )

    var frameIndex by remember { mutableStateOf(0) }
    var direction by remember { mutableStateOf(1) }

    LaunchedEffect(Unit) {
        while (true) {

            delay(300) // breathing speed

            frameIndex += direction

            if (frameIndex == frames.lastIndex || frameIndex == 0) {
                direction *= -1
            }
        }
    }

    Image(
        painter = painterResource(frames[frameIndex]),
        contentDescription = null,
        modifier = modifier
    )
}

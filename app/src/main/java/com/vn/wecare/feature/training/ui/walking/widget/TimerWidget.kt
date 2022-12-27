package com.vn.wecare.feature.training.ui.walking.widget

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun TimerWidget(
    modifier: Modifier,
    onResume: Boolean,
) {
    var ticks by remember { mutableStateOf(0) }
    var onTimerResume by remember { mutableStateOf(onResume) }
    onTimerResume = !onResume

    LaunchedEffect(key1 = onTimerResume) {
        while (onResume) {
            delay(1.seconds)
            ticks++
        }
    }
    CalculateTime(ticks = ticks)
}

@Composable
fun CalculateTime(ticks: Int) {
    val second: String = if (ticks % 60 < 10)
        "0${(ticks % 60).toString()}"
    else
        (ticks % 60).toString()
    val minute: String = if (ticks / 60 < 10)
        "0${(ticks / 60).toString()}"
    else
        (ticks / 60).toString()
    val hour: String = if (ticks / 3600 < 10)
        "0${(ticks / 3600).toString()}"
    else
        (ticks / 3600).toString()
    Text(
        text = "$hour:$minute:$second",
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
    )
}
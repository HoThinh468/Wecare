package com.vn.wecare.feature.exercises.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.feature.training.widget.CalculateTime
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Grey500
import com.vn.wecare.ui.theme.WeCareTypography
import kotlinx.coroutines.delay
import java.util.concurrent.CountDownLatch
import kotlin.time.Duration.Companion.seconds

@Preview
@Composable
fun a() {
    ProgressIndicator(duration = 5, onPlay = true)
}

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    duration: Int,
    onPlay: Boolean = true,
    onNavigationToNext: () -> Unit = {}
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        var ticks by remember { mutableStateOf(duration) }
        var onResume by remember {
            mutableStateOf(onPlay)
        }
        var progression by remember {
            mutableStateOf(1f)
        }

        onResume = !onPlay

        LaunchedEffect(key1 = onResume) {
            while (onPlay) {
                delay(1.seconds)
                ticks--
                if (ticks == 0) {
                    progression = 0f
                    onNavigationToNext()
                    break
                } else progression = ticks.toFloat() / duration.toFloat()
            }
        }
        CircularProgressIndicator(
            progress = 1f,
            modifier = modifier
                .size(120.dp),
            color = Grey500,
            strokeWidth = 12.dp
        )
        CircularProgressIndicator(
            progress = progression,
            modifier = modifier
                .size(120.dp)
                .graphicsLayer {
                    // Apply vertical flip transformation
                    scaleX = -1f
                },
            color = Green500,
            strokeWidth = 12.dp
        )
        CountDownTimer1(ticks = ticks)
    }
}

@Composable
fun CountDownTimer1(ticks: Int, style: TextStyle? = null ) {
    val second: String = if (ticks % 60 < 10)
        "0${(ticks % 60)}"
    else
        (ticks % 60).toString()
    val minute: String = if (ticks / 60 < 10)
        "0${(ticks / 60)}"
    else
        (ticks / 60).toString()
    Text(
        text = "$minute:$second",
        style = style ?: WeCareTypography.h2
    )
}
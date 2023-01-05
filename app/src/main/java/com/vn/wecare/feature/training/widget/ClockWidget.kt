package com.vn.wecare.feature.training.widget

import android.widget.TextClock
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ClockWidget(
    modifier: Modifier
) {
    AndroidView(
        // on below line we are initializing our text clock.
        factory = { context ->
            TextClock(context).apply {
                // on below line we are setting 12 hour format.
                format12Hour?.let { this.format12Hour = "hh:mm:ss a" }
                // on below line we are setting time zone.
                timeZone?.let { this.timeZone = it }
                // on below line we are setting text size.
                textSize.let { this.textSize = 32f }
            }
        },
        // on below line we are adding padding.
        modifier = modifier.padding(5.dp),
    )
}
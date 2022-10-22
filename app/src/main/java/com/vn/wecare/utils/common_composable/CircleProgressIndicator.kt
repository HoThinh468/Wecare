package com.vn.wecare.utils.common_composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressAnimated(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    color: Color = MaterialTheme.colors.primary,
    currentValue: Float = 75f,
    indicatorThickness: Dp = 12.dp,
    animationDuration: Int = 1000
) {
    val progressAnimationValue by animateFloatAsState(
        targetValue = currentValue,
        animationSpec = tween(animationDuration)
    )

    Box(
        modifier = Modifier
            .width(size)
            .height(size / 2),
        contentAlignment = Alignment.TopCenter
    ) {
        Canvas(
            modifier = modifier.width(size)
        ) {
            // Convert the currentValue to angle
            val sweepAngle = 100f * 180 / 100

            // Foreground indicator
            drawArc(
                color = color.copy(alpha = 0.3f),
                startAngle = 180f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = indicatorThickness.toPx(), cap = StrokeCap.Round),
                size = Size(
                    width = (size - indicatorThickness).toPx(),
                    height = (size - indicatorThickness).toPx()
                ),
                topLeft = Offset(
                    x = (indicatorThickness / 2).toPx(),
                    y = (indicatorThickness / 2).toPx()
                )
            )
        }

        // Draw the custom progress indicator with canvas
        Canvas(
            modifier = modifier.width(size)
        ) {
            // Convert the currentValue to angle
            val sweepAngle = (progressAnimationValue) * 180 / 100

            // Foreground indicator
            drawArc(
                color = color,
                startAngle = 180f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = indicatorThickness.toPx(), cap = StrokeCap.Round),
                size = Size(
                    width = (size - indicatorThickness).toPx(),
                    height = (size - indicatorThickness).toPx()
                ),
                topLeft = Offset(
                    x = (indicatorThickness / 2).toPx(),
                    y = (indicatorThickness / 2).toPx()
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CircularProgressAnimatedPreview() {
    CircularProgressAnimated()
}
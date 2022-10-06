package com.vn.wecare.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Green500,
    primaryVariant = Green300,
    secondary = Grey500,
    secondaryVariant = Grey100,
    onError = Red400,
    background = Color.White,
    surface = Grey100,
    onPrimary = Color.White,
    onSecondary = Black900,
    onBackground = Black900,
    onSurface = Black900,
)

@Composable
fun WecareTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = WeCareTypography,
        shapes = Shapes,
        content = content
    )
}
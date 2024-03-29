package com.vn.wecare.ui.theme

import androidx.compose.ui.graphics.Color

fun createColorWithPercentageFill(baseColor: Color, percentage: Float): Color {
    val alpha = percentage / 100f
    return baseColor.copy(alpha = alpha)
}

val Green500 = Color(0xFF7FB77E)
val Green300 = Color(0xFFB1D7B4)
val Red400 = Color(0xFFF94C66)
val Grey500 = Color(0xFFD1D1D1)
val Grey100 = Color(0xFFFAF9F9)
val Black900 = Color(0xFF343A40)
val Grey20 = Color(0x33C4C4C4)
val Blue = Color(0xFF3AB0FF)
val Yellow = Color(0xFFFFC090)
val YellowStar = Color(0xFFF9DC14)
val LightBlue = Color(0xFFF4FBFF)
val Black30 = createColorWithPercentageFill(Color.Black, 50f)
val Pink = Color(0xFFBFACE0)

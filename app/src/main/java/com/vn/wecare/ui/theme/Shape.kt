package com.vn.wecare.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp)
)

val roundedCornerShape = RoundedCornerShape(
    topStartPercent = 5, topEndPercent = 50, bottomStartPercent = 50, bottomEndPercent = 10
)
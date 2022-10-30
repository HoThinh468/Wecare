package com.vn.wecare.feature.training.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.ui.graphics.vector.ImageVector
import java.text.DecimalFormat

fun secondToMinUtil(
    duration: Int
): String {

    val min: Int = duration / 60
    val sec: Int = duration % 60

    return "${addZeroInStart(min)}:${addZeroInStart(sec)}"
}

fun secondToHourUtil(
    duration: Int
): String {

    val hour: Int = duration / 3600

    return "${addZeroInStart(hour)}:${secondToMinUtil(duration % 3600)}"
}

fun addZeroInStart(
    input: Int
): String = if (input < 10) "0$input"
else
    "$input"

fun stringWith2Decimals(
    double: Double
) : String {
    val df = DecimalFormat("#.##")
    return df.format(double)
}

enum class UserAction {
    WALKING {
        override fun actionIcon() = Icons.Default.DirectionsWalk
        override fun actionContent() = "Walking"
    },
    RUNNING {
        override fun actionIcon() = Icons.Default.DirectionsRun
        override fun actionContent() = "Running"
    },
    CYCLING {
        override fun actionIcon() = Icons.Default.DirectionsBike
        override fun actionContent() = "Cycling"
    };
    abstract fun actionIcon() : ImageVector
    abstract fun actionContent() : String
}
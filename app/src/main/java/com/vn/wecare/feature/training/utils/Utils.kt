package com.vn.wecare.feature.training.utils

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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
): String {
    val df = DecimalFormat("#,###.##")
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
    },
    MEDIATION {
        override fun actionIcon() = Icons.Default.SelfImprovement
        override fun actionContent() = "Mediation"
    };

    abstract fun actionIcon(): ImageVector
    abstract fun actionContent(): String
}

@SuppressLint("SimpleDateFormat")
fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("HH:mm - dd/MM/yyyy")
    return format.format(date)
}

fun convertStringToUserAction(action: String): UserAction {
    return when(action) {
        "WALKING" -> UserAction.WALKING
        "RUNNING" -> UserAction.RUNNING
        "CYCLING" -> UserAction.CYCLING
        else -> UserAction.MEDIATION
    }
}

fun convertUserActionToString(action: UserAction): String {
    return when(action) {
        UserAction.WALKING -> "WALKING"
        UserAction.RUNNING -> "RUNNING"
        UserAction.CYCLING -> "CYCLING"
        else -> "MEDIATION"
    }
}

fun formatNow(): String{
    val formatters: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-hh:mm:ss")
    return LocalDateTime.now().format(formatters)
}

fun formatCurrentMonth(): String{
    val formatters: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-yyyy")
    return LocalDateTime.now().format(formatters)
}
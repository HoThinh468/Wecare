package com.vn.wecare.utils

import android.util.Patterns
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern

private const val MIN_PASS_LENGTH = 6
private const val PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"

fun String.formatDate(): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    return this.format(formatter)
}

fun getDayId(day: Int, month: Int, year: Int): String {
    return "${day}_${month}_$year"
}

fun getCurrentDayId(): String {
    val now = LocalDate.now()
    return getDayId(now.dayOfMonth, now.monthValue, now.year)
}

fun getHourId(hour: Int, day: Int, month: Int, year: Int): String {
    return "${hour}_${day}_${month}_$year"
}

fun getCurrentHourId(): String {
    val now = LocalDateTime.now()
    return getHourId(now.hour, now.dayOfMonth, now.monthValue, now.year)
}

fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.isNotBlank() && this.length >= MIN_PASS_LENGTH
}

fun String.isValidUsername(): Boolean {
    return this.isNotBlank() && this.length >= MIN_PASS_LENGTH
}

fun String.passwordMatches(repeated: String): Boolean {
    return this == repeated
}

fun getMonthPrefix(month: Int): String {
    return when (month) {
        1 -> "Jan"
        2 -> "Feb"
        3 -> "Mar"
        4 -> "Apr"
        5 -> "May"
        6 -> "Jun"
        7 -> "Jul"
        8 -> "Aug"
        9 -> "Sep"
        10 -> "Oct"
        11 -> "Nov"
        else -> "Dec"
    }
}
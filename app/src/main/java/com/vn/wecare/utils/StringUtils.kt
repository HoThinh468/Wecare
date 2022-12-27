package com.vn.wecare.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
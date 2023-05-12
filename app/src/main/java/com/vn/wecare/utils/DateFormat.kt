package com.vn.wecare.utils

import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.Locale

fun getWeekdayFromTimestamp(timestamp: Long): String {
    val localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
    val weekday = localDateTime.dayOfWeek.toString()
    return weekday
}

fun getMondayOfTimestampWeek(timestamp: Long): LocalDate {
    val localDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).toLocalDate()
    return localDate.minusDays(localDate.dayOfWeek.value.toLong() - 1)
}

fun getSundayOfTimestampWeek(timestamp: Long): LocalDate {
    val localDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).toLocalDate()
    return localDate.plusDays(7 - localDate.dayOfWeek.value.toLong())
}

fun formatMonthDay(date: LocalDate): String {
    val month = date.month.getDisplayName(TextStyle.FULL, Locale.US)
    val day = date.dayOfMonth
    return "$month $day"
}

fun getFirstWeekdayTimestamp(timestamp: Long): Long {
    val instant = Instant.ofEpochMilli(timestamp)
    val zoneId = ZoneId.systemDefault()
    val localDate = instant.atZone(zoneId).toLocalDate()
    val firstWeekday = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    return firstWeekday.atStartOfDay(zoneId).toInstant().toEpochMilli()
}

fun getLastWeekdayTimestamp(timestamp: Long): Long {
    val instant = Instant.ofEpochMilli(timestamp)
    val zoneId = ZoneId.systemDefault()
    val localDate = instant.atZone(zoneId).toLocalDate()
    val lastWeekday = localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
    return lastWeekday.atStartOfDay(zoneId).toInstant().toEpochMilli()
}

fun getWeekDayFromInt(dayOfWeek: Int): String {
    return when (dayOfWeek) {
        1 -> "Mon"
        2 -> "Tue"
        3 -> "Wed"
        4 -> "Thu"
        5 -> "Fri"
        6 -> "Sat"
        7 -> "Sun"
        else -> ""
    }
}
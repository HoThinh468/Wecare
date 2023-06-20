package com.vn.wecare.utils

import android.annotation.SuppressLint
import android.util.Patterns
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

private const val MIN_PASS_LENGTH = 6

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

fun String.getNutrientIndexFromString(): Int {
    return this.dropLast(1).toInt()
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

fun getDayOfWeekPrefix(day: Int): String {
    return when (day) {
        1 -> "Sun"
        2 -> "Mon"
        3 -> "Tue"
        4 -> "Wed"
        5 -> "Thu"
        6 -> "Fri"
        else -> "Sat"
    }
}

@SuppressLint("SimpleDateFormat")
fun convertMonthAgoTimeStamp(timeStamp: Long): String {
    val currentTimestamp = System.currentTimeMillis()
    val simpleDate = SimpleDateFormat("M")
    return simpleDate.format(currentTimestamp - timeStamp) + " months ago"
}

@SuppressLint("SimpleDateFormat")
fun getDayFormatWithYear(dateTime: LocalDate): String {
    val simpleDateFormat = SimpleDateFormat("dd MMM, yyyy")
    return simpleDateFormat.format(
        Date.from(
            dateTime.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
        )
    )
}

@SuppressLint("SimpleDateFormat")
fun getDayFormatWithOnlyMonthPrefix(dateTime: LocalDate): String {
    val simpleDateFormat = SimpleDateFormat("dd MMM")
    return simpleDateFormat.format(
        Date.from(
            dateTime.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
        )
    )
}

fun caloriesFormatWithFloat(input: Float): String {
    val dec = DecimalFormat("#,###.##")
    return dec.format(input)
}

fun bmiFormatWithFloat(input: Float): String {
    val dec = DecimalFormat("##.#")
    return dec.format(input)
}

fun String.isLetters(): Boolean {
    return this.matches("^[a-z A-Z]*$".toRegex())
}
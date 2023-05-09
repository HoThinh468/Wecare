package com.vn.wecare.utils

import java.time.LocalDateTime

fun getEndOfTheDayMilliseconds(): Long {
    val now = LocalDateTime.now()
    val dateTime = java.util.Date(
        now.year,
        now.monthValue + 1,
        now.dayOfMonth,
        23,
        59
    )
    return dateTime.time
}
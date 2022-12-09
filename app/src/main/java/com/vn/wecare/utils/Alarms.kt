package com.vn.wecare.utils

import java.time.LocalDateTime
import java.time.ZoneId

private val current = LocalDateTime.now()

fun getCurrentEndHourInMillis(): Long {
    val currentDateInMillis = LocalDateTime.of(
        current.year, current.month, current.dayOfMonth, current.hour, 59
    )
    val currentZoneDateTime = currentDateInMillis.atZone(ZoneId.of("Vietnam"))
    return currentZoneDateTime.toInstant().toEpochMilli()
}

fun getNextEndHourInMillis(): Long {
    return getCurrentEndHourInMillis() + 3600000
}
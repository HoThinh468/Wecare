package com.vn.wecare.utils

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

fun getEndOfTheDayMilliseconds(): Long {
    return LocalDateTime.now().with(LocalTime.MAX).toInstant(OffsetDateTime.now().offset).toEpochMilli()
}
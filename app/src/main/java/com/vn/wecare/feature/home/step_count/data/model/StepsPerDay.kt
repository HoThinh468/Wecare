package com.vn.wecare.feature.home.step_count.data.model

import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class StepsPerDay(
    val dayId: String = "",
    val userId: String = "",
    val steps: Int = 0,
    val calories: Int = 0,
    val moveTime: Int = 0,
)

fun StepsPerDay.toEntity() = StepsPerDayEntity(
    this.dayId, this.userId, this.steps, this.calories, this.moveTime
)

fun String.fromDayIdToMillis(): Long {
    val formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy")
    val date = LocalDate.parse(this, formatter)

    // Get the timestamp in milliseconds from the LocalDate object
    return date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
}
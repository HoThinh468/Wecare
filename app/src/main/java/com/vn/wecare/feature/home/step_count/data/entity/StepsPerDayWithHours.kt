package com.vn.wecare.feature.home.step_count.data.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * This class captures the relationship between a [StepsPerDay] and a user's [StepsPerHour],
 * which is used by Room to fetch the related entities.
 */
data class StepsPerDayWithHours(
    @Embedded val stepsPerDay: StepsPerDayEntity,
    @Relation(
        parentColumn = "day_id",
        entityColumn = "day"
    )
    val stepsPerHours: List<StepsPerHourEntity> = emptyList()
)
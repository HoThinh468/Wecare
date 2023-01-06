package com.vn.wecare.feature.home.step_count.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import javax.annotation.Nullable

data class StepsPerDayWithHours(
    @Embedded val stepsPerDayEntity: StepsPerDayEntity,
    @Relation(
        parentColumn = "dayId",
        entityColumn = "dayIncludeId"
    )
    val hour: StepsPerHourEntity
)
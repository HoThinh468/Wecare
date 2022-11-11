package com.vn.wecare.feature.home.step_count.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "steps_per_hour",
    primaryKeys = ["hour", "day_id"]
)
data class StepsPerHourEntity(
    @NonNull @ColumnInfo(name = "hour") val hour: Int,
    @NonNull @ColumnInfo(name = "day") val day: String,
    val steps: Int,
    val calories: Int,
    val time: Int,
)
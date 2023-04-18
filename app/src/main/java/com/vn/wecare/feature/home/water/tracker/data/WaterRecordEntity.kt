package com.vn.wecare.feature.home.water.tracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.vn.wecare.core.data.DateTimeConverters
import java.util.Calendar

@Entity(tableName = "water_record")
data class WaterRecordEntity(
    @PrimaryKey(autoGenerate = true) val recordId: Int = 0,
    val userId: String = "",
    val dayId: String = "",
    val amount: Int = 0,
    @TypeConverters(DateTimeConverters::class) val dateTime: Calendar
)
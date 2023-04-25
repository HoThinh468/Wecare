package com.vn.wecare.feature.home.water.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.firebase.Timestamp
import com.vn.wecare.core.data.DateTimeConverters
import java.util.Calendar

@Entity(tableName = "water_record")
data class WaterRecordEntity(
    @PrimaryKey val recordId: String = "",
    val userId: String = "",
    val dayId: String = "",
    val amount: Int = 0,
    @ColumnInfo(defaultValue = "2000")
    val currentTarget: Int = 2000,
    @TypeConverters(DateTimeConverters::class) val dateTime: Calendar
)

fun WaterRecordEntity.toModel(): WaterRecord {
    return WaterRecord(
        recordId = this.recordId,
        userId = this.userId,
        dayId = this.dayId,
        amount = this.amount,
        currentTarget = this.currentTarget,
        dateTime = Timestamp(this.dateTime.time)
    )
}
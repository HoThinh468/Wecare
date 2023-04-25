package com.vn.wecare.feature.home.water.data.model

import com.google.firebase.Timestamp
import java.util.Calendar

data class WaterRecord(
    val recordId: String = "",
    val userId: String = "",
    val dayId: String = "",
    val amount: Int = 0,
    val currentTarget: Int = 2000,
    val dateTime: Timestamp = Timestamp(Calendar.getInstance().time)
)

fun WaterRecord.toEntity(): WaterRecordEntity {
    val calendar = Calendar.getInstance()
    calendar.time = this.dateTime.toDate()
    return WaterRecordEntity(
        recordId = this.recordId,
        userId = this.userId,
        dayId = this.dayId,
        amount = this.amount,
        currentTarget = this.currentTarget,
        dateTime = calendar
    )
}
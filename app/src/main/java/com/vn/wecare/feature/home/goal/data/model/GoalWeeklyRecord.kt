package com.vn.wecare.feature.home.goal.data.model

import android.os.Parcel
import android.os.Parcelable

data class GoalWeeklyRecord(
    val startDate: Long = 0,
    val endDate: Long = 0,
    val caloriesIn: Int = 0,
    val caloriesOut: Int = 0,
    val proteinAmount: Int = 0,
    val fatAmount: Int = 0,
    val carbsAmount: Int = 0,
    val weeklyGoalWeight: Float = 0f,
    val weeklyCaloriesGoal: Int = 0,
    val weeklyCaloriesOutGoal: Int = 0,
    val status: String = GoalStatus.NOTSTARTED.value,
    val numberOfDayRecord: Int = 0,
    val bmr: Int = 0,
    val goalName: String = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(startDate)
        parcel.writeLong(endDate)
        parcel.writeInt(caloriesIn)
        parcel.writeInt(caloriesOut)
        parcel.writeInt(proteinAmount)
        parcel.writeInt(fatAmount)
        parcel.writeInt(carbsAmount)
        parcel.writeFloat(weeklyGoalWeight)
        parcel.writeInt(weeklyCaloriesGoal)
        parcel.writeInt(weeklyCaloriesOutGoal)
        parcel.writeString(status)
        parcel.writeInt(numberOfDayRecord)
        parcel.writeInt(bmr)
        parcel.writeString(goalName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GoalWeeklyRecord> {
        override fun createFromParcel(parcel: Parcel): GoalWeeklyRecord {
            return GoalWeeklyRecord(parcel)
        }

        override fun newArray(size: Int): Array<GoalWeeklyRecord?> {
            return arrayOfNulls(size)
        }

        const val numberOfDayRecordField = "numberOfDayRecord"
        const val statusField = "status"
    }
}
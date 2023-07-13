package com.vn.wecare.feature.home.goal.data.model

import android.os.Parcel
import android.os.Parcelable

data class GoalWeeklyRecord(
    val startDate: Long = 0,
    val endDate: Long = 0,
    val caloriesIn: Int = 0,
    val caloriesOut: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(), parcel.readLong(), parcel.readInt(), parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(startDate)
        parcel.writeLong(endDate)
        parcel.writeInt(caloriesIn)
        parcel.writeInt(caloriesOut)
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
    }

}
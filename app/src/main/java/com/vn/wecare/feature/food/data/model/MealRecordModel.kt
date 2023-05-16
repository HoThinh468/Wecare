package com.vn.wecare.feature.food.data.model

import android.os.Parcel
import android.os.Parcelable

data class MealRecordModel(
    val id: Long = 0,
    val title: String = "",
    val imgUrl: String = "",
    val calories: Int = 0,
    val protein: String = "0g",
    val fat: String = "0g",
    val carbs: String = "0g",
    val quantity: Int = 1
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(imgUrl)
        parcel.writeInt(calories)
        parcel.writeString(protein)
        parcel.writeString(fat)
        parcel.writeString(carbs)
        parcel.writeInt(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MealRecordModel> {
        override fun createFromParcel(parcel: Parcel): MealRecordModel {
            return MealRecordModel(parcel)
        }

        override fun newArray(size: Int): Array<MealRecordModel?> {
            return arrayOfNulls(size)
        }
    }
}
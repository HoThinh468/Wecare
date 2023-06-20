package com.vn.wecare.feature.food.data.model

import android.os.Parcel
import android.os.Parcelable

data class Meal(
    val id: Long = 0,
    val name: String = "",
    val imgUrl: String = "https://images.unsplash.com/photo-1563455915098-ea411b44da3c?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=387&q=80",
    val category: String = MealTypeKey.BREAKFAST.value,
    val calories: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val carbs: Int = 0,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(imgUrl)
        parcel.writeString(category)
        parcel.writeInt(calories)
        parcel.writeInt(protein)
        parcel.writeInt(fat)
        parcel.writeInt(carbs)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Meal> {
        override fun createFromParcel(parcel: Parcel): Meal {
            return Meal(parcel)
        }

        override fun newArray(size: Int): Array<Meal?> {
            return arrayOfNulls(size)
        }
    }

}

fun Meal.toMealByNutrients(): MealByNutrients = MealByNutrients(
    id = this.id,
    title = this.name,
    imgUrl = this.imgUrl,
    calories = this.calories,
    protein = "${this.protein}g",
    fat = "${this.fat}g",
    carbs = "${this.carbs}g",
    imageType = "jpg"
)
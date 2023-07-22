package com.vn.wecare.feature.home.goal.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.vn.wecare.R
import com.vn.wecare.utils.WecareUserConstantValues.DEFAULT_TIME_TO_REACH_GOAL_IN_WEEK
import com.vn.wecare.utils.WecareUserConstantValues.DEFAULT_WEIGHT_DIFFERENCE_IN_KG
import com.vn.wecare.utils.WecareUserConstantValues.GAIN_MUSCLE
import com.vn.wecare.utils.WecareUserConstantValues.GAIN_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.GET_HEALTHIER
import com.vn.wecare.utils.WecareUserConstantValues.LOSE_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MAINTAIN_WEIGHT

data class Goal(
    val goalId: String = "",
    val goalName: String = EnumGoal.MAINTAINWEIGHT.value,
    val caloriesInEachDayGoal: Int = 0,
    val caloriesBurnedEachDayGoal: Int = 0,
    val caloriesBurnedGoalForStepCount: Int = 0,
    val stepsGoal: Int = 0,
    val moveTimeGoal: Int = 0,
    val weightDifference: Int = DEFAULT_WEIGHT_DIFFERENCE_IN_KG,
    val timeToReachGoalInWeek: Int = DEFAULT_TIME_TO_REACH_GOAL_IN_WEEK,
    val dateSetGoal: Long = 0L,
    val dateEndGoal: Long = 0L,
    val goalStatus: String = GoalStatus.INPROGRESS.value,
    val weeklyGoalWeight: Float = 0f,
    val oldWeight: Int = 0,
    val newWeightAssumed: Int = 0,
    val bmr: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(goalId)
        parcel.writeString(goalName)
        parcel.writeInt(caloriesInEachDayGoal)
        parcel.writeInt(caloriesBurnedEachDayGoal)
        parcel.writeInt(caloriesBurnedGoalForStepCount)
        parcel.writeInt(stepsGoal)
        parcel.writeInt(moveTimeGoal)
        parcel.writeInt(weightDifference)
        parcel.writeInt(timeToReachGoalInWeek)
        parcel.writeLong(dateSetGoal)
        parcel.writeLong(dateEndGoal)
        parcel.writeString(goalStatus)
        parcel.writeFloat(weeklyGoalWeight)
        parcel.writeInt(oldWeight)
        parcel.writeInt(newWeightAssumed)
        parcel.writeInt(bmr)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Goal> {
        override fun createFromParcel(parcel: Parcel): Goal {
            return Goal(parcel)
        }

        override fun newArray(size: Int): Array<Goal?> {
            return arrayOfNulls(size)
        }
    }
}

enum class EnumGoal(val value: String, @DrawableRes val imgRes: Int) {
    GAINMUSCLE(GAIN_MUSCLE, R.drawable.img_illu_gain_weight), LOSEWEIGHT(
        LOSE_WEIGHT, R.drawable.img_illu_loose_weight
    ),
    GETHEALTHIER(GET_HEALTHIER, R.drawable.img_illu_gain_weight), NULL(
        "NULL",
        R.drawable.img_illu_gain_weight
    ),
    GAINWEIGHT(
        GAIN_WEIGHT, R.drawable.img_illu_gain_weight
    ),
    MAINTAINWEIGHT(MAINTAIN_WEIGHT, R.drawable.img_illu_maintain_weight);

    companion object {
        fun getEnumGoalFromValue(value: String): EnumGoal {
            return when (value) {
                GAIN_WEIGHT -> GAINWEIGHT
                MAINTAIN_WEIGHT -> MAINTAINWEIGHT
                else -> LOSEWEIGHT
            }
        }
    }
}
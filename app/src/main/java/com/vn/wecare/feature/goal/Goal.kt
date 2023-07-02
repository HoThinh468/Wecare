package com.vn.wecare.feature.goal

import com.vn.wecare.utils.WecareUserConstantValues.DEFAULT_TIME_TO_REACH_GOAL_IN_WEEK
import com.vn.wecare.utils.WecareUserConstantValues.DEFAULT_WEIGHT_DIFFERENCE_IN_KG
import com.vn.wecare.utils.WecareUserConstantValues.GAIN_MUSCLE
import com.vn.wecare.utils.WecareUserConstantValues.GET_HEALTHIER
import com.vn.wecare.utils.WecareUserConstantValues.IMPROVE_MOOD
import com.vn.wecare.utils.WecareUserConstantValues.LOSE_WEIGHT
import java.time.LocalDate

data class Goal(
    val userId: String = "",
    val goalName: String = EnumGoal.IMPROVEMOOD.value,
    val caloriesInEachDayGoal: Int = 0,
    val caloriesBurnedEachDayGoal: Int = 0,
    val stepsGoal: Int = 0,
    val moveTimeGoal: Int = 0,
    val weightDifference: Int = DEFAULT_WEIGHT_DIFFERENCE_IN_KG,
    val timeToReachGoal: Int = DEFAULT_TIME_TO_REACH_GOAL_IN_WEEK,
    val dateSetGoal: Long = 0L,
    val dateEndGoal: Long = 0L
)

enum class EnumGoal(val value: String) {
    GAINMUSCLE(GAIN_MUSCLE), LOSEWEIGHT(LOSE_WEIGHT), GETHEALTHIER(GET_HEALTHIER), IMPROVEMOOD(
        IMPROVE_MOOD
    )
}
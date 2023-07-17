package com.vn.wecare.feature.food

import com.vn.wecare.core.WecareUserSingletonObject
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.ActivityLevel
import com.vn.wecare.utils.WecareUserConstantValues.GAIN_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.LOSE_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MAINTAIN_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_AGE
import com.vn.wecare.utils.WecareUserConstantValues.MIN_HEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.NUMBER_OF_DAYS_IN_WEEK
import com.vn.wecare.utils.WecareUserConstantValues.ONE_KG_TO_CALORIES
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UserCaloriesAmount(
    val caloriesInEachDay: Int = 0,
    val caloriesOutEachDay: Int = 0,
    val caloriesOfBreakfast: Int = 0,
    val caloriesOfLunch: Int = 0,
    val caloriesOfSnack: Int = 0,
    val caloriesOfDinner: Int = 0,
)

object WecareCaloriesObject {
    private val instance = MutableStateFlow(UserCaloriesAmount())

    fun getInstance() = instance.value

    fun getInstanceFlow() = instance.asStateFlow()

    fun updateUserCaloriesAmount() {
        val user = WecareUserSingletonObject.getInstance()
        val bmr = getBMR(
            user.weight ?: MIN_WEIGHT,
            user.height ?: MIN_HEIGHT,
            user.gender ?: true,
            user.age ?: MIN_AGE
        )
        val tdee = getTotalDailyEnergyExpenditure(
            bmr, ActivityLevel.getActivityLevelFromValue(user.activityLevel)
        )

        val weeklyGoalWeight = LatestGoalSingletonObject.getInStance().weeklyGoalWeight
        val caloriesIn = getDailyCaloriesIn(tdee, user.goal ?: MAINTAIN_WEIGHT, weeklyGoalWeight)
        val caloriesOut = getDailyCaloriesOutBasedOnActivity(
            tdee,
            user.goal ?: MAINTAIN_WEIGHT,
            ActivityLevel.getActivityLevelFromValue(user.activityLevel),
            weeklyGoalWeight
        )

        updateCaloriesInAmountOfDay(caloriesIn)
        updateCaloriesOutAmountOfDay(caloriesOut)
    }

    fun getBMR(
        weight: Int, height: Int, gender: Boolean, age: Int
    ): Int {
        return if (gender) {
            (6.25 * height).toInt() + (10 * weight) - (5 * age) + 5
        } else {
            (6.25 * height).toInt() + (10 * weight) - (5 * age) - 161
        }
    }

    fun getTotalDailyEnergyExpenditure(
        bmr: Int, activityLevel: ActivityLevel,
    ): Int {
        return (bmr * activityLevel.caloriesIndex).toInt()
    }

    fun getDailyCaloriesIn(tdee: Int, goal: String, weeklyGoalWeight: Float): Int {
        return if (goal == GAIN_WEIGHT) {
            tdee + getDailyExcessCaloriesBasedOnWeeklyGoalWeight(weeklyGoalWeight)
        } else {
            tdee
        }
    }

    fun getDailyCaloriesOutBasedOnActivity(
        tdee: Int, goal: String, activityLevel: ActivityLevel, weeklyGoalWeight: Float
    ): Int {
        val bmr = tdee / activityLevel.caloriesIndex
        val physicalActivityAmount = (tdee - bmr).toInt()
        return if (goal == LOSE_WEIGHT) {
            physicalActivityAmount + getDailyExcessCaloriesBasedOnWeeklyGoalWeight(weeklyGoalWeight)
        } else {
            physicalActivityAmount
        }
    }

    private fun updateCaloriesInAmountOfDay(calories: Int) {
        instance.update {
            it.copy(
                caloriesInEachDay = calories,
                caloriesOfBreakfast = getCaloriesForBreakfast(calories),
                caloriesOfLunch = getCaloriesForLunch(calories),
                caloriesOfSnack = getCaloriesForSnack(calories),
                caloriesOfDinner = getCaloriesForDinner(calories)
            )
        }
    }


    private fun updateCaloriesOutAmountOfDay(caloriesOut: Int) {
        instance.update { it.copy(caloriesOutEachDay = caloriesOut) }
    }

    private fun getCaloriesForBreakfast(caloriesIn: Int) = caloriesIn.times(0.3).toInt()

    private fun getCaloriesForLunch(caloriesIn: Int) = caloriesIn.times(0.35).toInt()

    private fun getCaloriesForDinner(caloriesIn: Int) = caloriesIn.times(0.25).toInt()

    private fun getCaloriesForSnack(caloriesIn: Int) = caloriesIn.times(0.1).toInt()

    private fun getDailyExcessCaloriesBasedOnWeeklyGoalWeight(weeklyGoalWeight: Float): Int {
        return ((weeklyGoalWeight * ONE_KG_TO_CALORIES) / NUMBER_OF_DAYS_IN_WEEK).toInt()
    }
}
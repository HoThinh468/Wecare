package com.vn.wecare.feature.onboarding.usecase

import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.feature.goal.EnumGoal
import com.vn.wecare.feature.goal.Goal
import com.vn.wecare.utils.WecareUserConstantValues
import com.vn.wecare.utils.WecareUserConstantValues.DAY_TO_MILLISECONDS
import com.vn.wecare.utils.WecareUserConstantValues.DEFAULT_CALORIES_TO_BURN_EACH_DAY
import com.vn.wecare.utils.WecareUserConstantValues.DEFAULT_CALORIES_TO_BURN_EACH_DAY_TO_GAIN_MUSCLE
import com.vn.wecare.utils.WecareUserConstantValues.DEFAULT_CALORIES_TO_BURN_EACH_DAY_TO_LOSE_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.NUMBER_OF_DAYS_IN_WEEK
import javax.inject.Inject

class DefineGoalBasedOnInputsUsecase @Inject constructor() {
    fun getGoalFromInputs(
        userId: String,
        goal: EnumGoal,
        height: Int,
        weight: Int,
        age: Int,
        gender: Boolean,
        weightDifference: Int,
        timeToReachGoal: Int
    ): Goal {
        val caloriesInEachDay = WecareCaloriesObject.calculateCaloriesInWithUserPersonalInfo(
            goal.value, weight, height, gender, age
        )
        val caloriesOutEachDay = WecareCaloriesObject.calculateCaloriesOutBasedOnCaloriesIn(
            caloriesInEachDay, goal.value
        )
        val stepsGoal = when (goal) {
            EnumGoal.GAINMUSCLE -> WecareUserConstantValues.STEP_GOAL_FOR_GAIN_MUSCLE
            EnumGoal.LOSEWEIGHT -> WecareUserConstantValues.STEP_GOAL_FOR_LOSE_WEIGHT
            EnumGoal.GETHEALTHIER -> WecareUserConstantValues.STEP_GOAL_FOR_GET_HEALTHIER
            else -> WecareUserConstantValues.STEP_GOAL_FOR_IMPROVE_MOOD
        }
        val moveTimeGoal = when (goal) {
            EnumGoal.GAINMUSCLE -> WecareUserConstantValues.MOVE_TIME_FOR_GAIN_MUSCLE
            EnumGoal.LOSEWEIGHT -> WecareUserConstantValues.MOVE_TIME_FOR_LOSE_WEIGHT
            EnumGoal.GETHEALTHIER -> WecareUserConstantValues.MOVE_TIME_FOR_GET_HEALTHIER
            else -> WecareUserConstantValues.MOVE_TIME_FOR_IMPROVE_MOOD
        }
        val currentDateTime = System.currentTimeMillis()
        return Goal(
            userId = userId,
            goalName = goal.value,
            caloriesInEachDayGoal = caloriesInEachDay,
            caloriesBurnedEachDayGoal = caloriesOutEachDay,
            stepsGoal = stepsGoal,
            moveTimeGoal = moveTimeGoal,
            timeToReachGoal = timeToReachGoal,
            weightDifference = weightDifference,
            dateSetGoal = currentDateTime,
            dateEndGoal = currentDateTime.plus(timeToReachGoal * NUMBER_OF_DAYS_IN_WEEK * DAY_TO_MILLISECONDS)
        )
    }
}
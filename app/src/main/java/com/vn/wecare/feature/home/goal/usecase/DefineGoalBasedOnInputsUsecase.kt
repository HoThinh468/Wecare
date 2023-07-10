package com.vn.wecare.feature.home.goal.usecase

import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.data.model.Goal
import com.vn.wecare.utils.WecareUserConstantValues
import com.vn.wecare.utils.WecareUserConstantValues.DEFAULT_TIME_FOR_EACH_GOAL_IN_WEEK
import com.vn.wecare.utils.WecareUserConstantValues.WEEK_TO_MILLISECONDS
import javax.inject.Inject

class DefineGoalBasedOnInputsUsecase @Inject constructor() {
    fun getGoalFromInputs(
        goal: EnumGoal,
        height: Int,
        weight: Int,
        age: Int,
        gender: Boolean,
        weightDifference: Int?,
        timeToReachGoal: Int?
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
        val requiredTime = timeToReachGoal ?: DEFAULT_TIME_FOR_EACH_GOAL_IN_WEEK
        val endDate = currentDateTime.plus(requiredTime * WEEK_TO_MILLISECONDS)
        return Goal(
            goalId = currentDateTime.toString(),
            goalName = goal.value,
            caloriesInEachDayGoal = caloriesInEachDay,
            caloriesBurnedEachDayGoal = caloriesOutEachDay,
            stepsGoal = stepsGoal,
            moveTimeGoal = moveTimeGoal,
            timeToReachGoalInWeek = requiredTime,
            weightDifference = weightDifference ?: 0,
            dateSetGoal = currentDateTime,
            dateEndGoal = endDate,
        )
    }
}
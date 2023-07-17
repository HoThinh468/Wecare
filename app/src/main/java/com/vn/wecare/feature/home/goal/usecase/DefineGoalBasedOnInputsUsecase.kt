package com.vn.wecare.feature.home.goal.usecase

import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.feature.home.goal.data.model.ActivityLevel
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.data.model.Goal
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
        timeToReachGoal: Int?,
        weeklyGoalWeight: Float,
        activityLevel: ActivityLevel
    ): Goal {
        val bmr = WecareCaloriesObject.getBMR(
            weight, height, gender, age
        )
        val tdee = WecareCaloriesObject.getTotalDailyEnergyExpenditure(bmr, activityLevel)
        val caloriesOutEachDay = WecareCaloriesObject.getDailyCaloriesOutBasedOnActivity(
            tdee, goal.value, activityLevel, weeklyGoalWeight
        )
        val caloriesInEachDay = WecareCaloriesObject.getDailyCaloriesIn(
            tdee, goal.value, weeklyGoalWeight
        )
        val caloriesOutGoalForStepCount = caloriesOutEachDay / 3
        val stepsGoal = (caloriesOutEachDay * 2000) / (2.9 * weight)
        val moveTimeGoal = stepsGoal / 100
        val currentDateTime = System.currentTimeMillis()
        val requiredTime = timeToReachGoal ?: DEFAULT_TIME_FOR_EACH_GOAL_IN_WEEK
        val endDate = currentDateTime.plus(requiredTime * WEEK_TO_MILLISECONDS)
        return Goal(
            goalId = currentDateTime.toString(),
            goalName = goal.value,
            caloriesInEachDayGoal = caloriesInEachDay,
            caloriesBurnedEachDayGoal = caloriesOutEachDay,
            caloriesBurnedGoalForStepCount = caloriesOutGoalForStepCount,
            stepsGoal = stepsGoal.toInt(),
            moveTimeGoal = moveTimeGoal.toInt(),
            timeToReachGoalInWeek = requiredTime,
            weightDifference = weightDifference ?: 0,
            dateSetGoal = currentDateTime,
            dateEndGoal = endDate,
            weeklyGoalWeight = weeklyGoalWeight,
        )
    }
}
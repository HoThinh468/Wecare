package com.vn.wecare.feature.home.goal.usecase

import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.feature.home.goal.data.model.ActivityLevel
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.data.model.Goal
import com.vn.wecare.feature.home.step_count.StepsUtil
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
            tdee, activityLevel
        )
        val caloriesInEachDay = WecareCaloriesObject.getDailyCaloriesIn(
            tdee, goal.value, weeklyGoalWeight
        )
        val caloriesOutGoalForStepCount = caloriesOutEachDay / 2
        val stepsGoal =
            StepsUtil.getStepsByCaloriesBurned(caloriesOutGoalForStepCount, height, weight)
        val moveTimeGoal = StepsUtil.getMoveTimeRequiredBySteps(stepsGoal, height) / 60
        val currentDateTime = System.currentTimeMillis()
        val requiredTime = timeToReachGoal ?: DEFAULT_TIME_FOR_EACH_GOAL_IN_WEEK
        val endDate = currentDateTime.plus(requiredTime * WEEK_TO_MILLISECONDS)
        return Goal(
            goalId = currentDateTime.toString(),
            goalName = goal.value,
            caloriesInEachDayGoal = caloriesInEachDay,
            caloriesBurnedEachDayGoal = caloriesOutEachDay,
            caloriesBurnedGoalForStepCount = caloriesOutGoalForStepCount,
            stepsGoal = stepsGoal,
            moveTimeGoal = moveTimeGoal,
            timeToReachGoalInWeek = requiredTime,
            weightDifference = weightDifference ?: 0,
            dateSetGoal = currentDateTime,
            dateEndGoal = endDate,
            weeklyGoalWeight = weeklyGoalWeight,
            oldWeight = weight,
            bmr = bmr
        )
    }
}
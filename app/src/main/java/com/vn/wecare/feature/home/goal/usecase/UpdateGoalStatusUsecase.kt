package com.vn.wecare.feature.home.goal.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.goal.data.GoalsRepository
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.data.model.Goal
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import com.vn.wecare.utils.WecareUserConstantValues.NUMBER_OF_DAYS_IN_WEEK
import com.vn.wecare.utils.getProgressInFloatWithFloatInput
import com.vn.wecare.utils.getProgressInFloatWithIntInput
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateGoalStatusUsecase @Inject constructor(
    private val repo: GoalsRepository
) {
    fun update(goalId: String, status: GoalStatus) = repo.updateGoalStatus(goalId, status.value)

    fun autoUpdateForGainMuscleAndLooseWeight(
        goal: Goal, totalCaloriesIn: Int, totalCaloriesOut: Int
    ): Flow<Response<Boolean>> {
        val caloriesDifference = if (goal.goalName == EnumGoal.GAINMUSCLE.value) {
            totalCaloriesIn - totalCaloriesOut
        } else totalCaloriesOut - totalCaloriesIn
        val weightDifference = caloriesDifference.toFloat() / 7700f
        val weightProgress =
            getProgressInFloatWithFloatInput(weightDifference, goal.weightDifference.toFloat())

        val caloriesInGoalForAllWeeks =
            goal.caloriesInEachDayGoal * NUMBER_OF_DAYS_IN_WEEK * goal.timeToReachGoalInWeek
        val caloriesInProgress =
            getProgressInFloatWithIntInput(totalCaloriesIn, caloriesInGoalForAllWeeks)

        val caloriesOutGoalForAllWeeks =
            goal.caloriesBurnedEachDayGoal * NUMBER_OF_DAYS_IN_WEEK * goal.timeToReachGoalInWeek
        val caloriesOutProgress =
            getProgressInFloatWithIntInput(totalCaloriesOut, caloriesOutGoalForAllWeeks)

        val newStatus =
            if (weightProgress > 0.8f && caloriesInProgress > 0.8f && caloriesOutProgress > 0.8f) {
                GoalStatus.SUCCESS
            } else GoalStatus.DONE

        return repo.updateGoalStatus(goal.goalId, newStatus.value)
    }
}
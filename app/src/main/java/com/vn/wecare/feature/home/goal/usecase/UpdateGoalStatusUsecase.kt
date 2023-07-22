package com.vn.wecare.feature.home.goal.usecase

import android.util.Log
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.goal.data.GoalsRepository
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.data.model.Goal
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.feature.home.goal.utils.generateGoalWeeklyRecordIdWithGoal
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

        val newStatus = GoalStatus.DONE

        return repo.updateGoalStatus(goal.goalId, newStatus.value)
    }

    fun updateGoalStatusForWeeklyGoal(status: GoalStatus, weeklyGoal: GoalWeeklyRecord) =
        repo.updateInfoForGoalWeeklyRecord(GoalWeeklyRecord.statusField, status.value, weeklyGoal)

    suspend fun updatePreviousGoalWeeklyRecords(goalId: String) {
        repo.getOldWeeklyRecord(goalId).collect { resList ->
            if (resList is Response.Success) {
                for (record in resList.data) {
                    updateGoalStatusForWeeklyGoal(GoalStatus.DONE, record).collect {
                        if (it is Response.Error) {
                            Log.d(
                                "Goal track",
                                "Fail to update status for ended weekly goal with id ${
                                    generateGoalWeeklyRecordIdWithGoal(record)
                                }"
                            )
                        }
                    }
                }
            }
        }
    }

    suspend fun updateWeeklyGoalStatusWhenGoalIsCanceled(goalId: String) {
        repo.getAllWeeklyRecordsOfAGoal(goalId).collect { resList ->
            if (resList is Response.Success) {
                for (record in resList.data) {
                    updateGoalStatusForWeeklyGoal(GoalStatus.CANCELED, record).collect {
                        if (it is Response.Error) {
                            Log.d(
                                "Goal track",
                                "Fail to update status when canceled goal for weekly goal with id ${
                                    generateGoalWeeklyRecordIdWithGoal(record)
                                }"
                            )
                        }
                    }
                }
            }
        }
    }
}
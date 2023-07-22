package com.vn.wecare.feature.home.goal.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.goal.data.CurrentGoalDailyRecordSingletonObject
import com.vn.wecare.feature.home.goal.data.CurrentGoalWeeklyRecordSingletonObject
import com.vn.wecare.feature.home.goal.data.GoalsRepository
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.GoalDailyRecord
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

private const val CALORIES_OUT_FIELD = "caloriesOut"

class UpdateGoalRecordUsecase @Inject constructor(
    private val repository: GoalsRepository,
    private val insertGoalDailyRecordUsecase: InsertGoalDailyRecordUsecase
) {
    suspend fun updateCaloriesInForCurrentDayRecord(
        caloriesIn: Int, protein: Int, fat: Int, carbs: Int
    ) {
        if (LatestGoalSingletonObject.getInStance().goalStatus != GoalStatus.INPROGRESS.value) return
        val currentDailyRecord = CurrentGoalDailyRecordSingletonObject.getInstance()

        if (currentDailyRecord == GoalDailyRecord()) {
            insertGoalDailyRecordUsecase.whenAddCaloriesInRecord(caloriesIn, protein, fat, carbs)
            return
        }

        val currentCalories = currentDailyRecord.caloriesIn + caloriesIn
        val currentProtein = currentDailyRecord.proteinAmount + protein
        val currentFat = currentDailyRecord.fatAmount + fat
        val currentCarbs = currentDailyRecord.carbsAmount + carbs

        val newRecord = currentDailyRecord.copy(
            caloriesIn = currentCalories,
            carbsAmount = currentCarbs,
            proteinAmount = currentProtein,
            fatAmount = currentFat
        )

        repository.insertGoalDailyRecord(newRecord).collect()
        CurrentGoalDailyRecordSingletonObject.updateInstance(newRecord)
    }

    suspend fun updateCaloriesInForCurrentWeekRecord(
        caloriesIn: Int, protein: Int, fat: Int, carbs: Int
    ) {
        if (LatestGoalSingletonObject.getInStance().goalStatus != GoalStatus.INPROGRESS.value) return
        val currentWeeklyRecord = CurrentGoalWeeklyRecordSingletonObject.getInstance()

        val currentWeeklyCaloriesIn = currentWeeklyRecord.caloriesIn + caloriesIn
        val currentProtein = currentWeeklyRecord.proteinAmount + protein
        val currentFat = currentWeeklyRecord.fatAmount + fat
        val currentCarbs = currentWeeklyRecord.carbsAmount + carbs
        val newRecord = currentWeeklyRecord.copy(
            caloriesIn = currentWeeklyCaloriesIn,
            proteinAmount = currentProtein,
            fatAmount = currentFat,
            carbsAmount = currentCarbs,
            status = GoalStatus.INPROGRESS.value
        )

        repository.insertGoalWeeklyRecord(
            latestGoalId = LatestGoalSingletonObject.getInStance().goalId, record = newRecord
        ).collect()
        CurrentGoalWeeklyRecordSingletonObject.updateInstance(
            currentWeeklyRecord.copy(caloriesIn = currentWeeklyCaloriesIn)
        )
    }

    suspend fun updateCaloriesOutForCurrentDayRecord(caloriesOut: Int) {
        val currentDailyRecord = CurrentGoalDailyRecordSingletonObject.getInstance()

        if (currentDailyRecord == GoalDailyRecord()) {
            insertGoalDailyRecordUsecase.whenAddCaloriesOutRecord(caloriesOut)
            return
        }

        val currentCalories = currentDailyRecord.caloriesOut + caloriesOut
        repository.updateCaloriesAmountForGoalDailyRecord(
            field = CALORIES_OUT_FIELD, recordDay = currentDailyRecord.day, value = currentCalories
        ).collect {
            if (it is Response.Success) {
                CurrentGoalDailyRecordSingletonObject.updateInstance(
                    currentDailyRecord.copy(caloriesOut = currentCalories)
                )
            }
        }
    }

    suspend fun updateCaloriesOutForCurrentWeekRecord(calories: Int) {
        if (LatestGoalSingletonObject.getInStance().goalStatus != GoalStatus.INPROGRESS.value) return
        val currentWeeklyRecord = CurrentGoalWeeklyRecordSingletonObject.getInstance()

        val currentWeeklyCaloriesOut = currentWeeklyRecord.caloriesOut + calories

        repository.updateInfoForGoalWeeklyRecord(
            CALORIES_OUT_FIELD, currentWeeklyCaloriesOut, currentWeeklyRecord
        ).collect {
            if (it is Response.Success) {
                CurrentGoalWeeklyRecordSingletonObject.updateInstance(
                    currentWeeklyRecord.copy(caloriesOut = currentWeeklyCaloriesOut)
                )
            }
        }
    }
}
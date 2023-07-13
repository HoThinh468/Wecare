package com.vn.wecare.feature.home.goal.usecase

import com.vn.wecare.feature.home.goal.data.CurrentGoalDailyRecordSingletonObject
import com.vn.wecare.feature.home.goal.data.CurrentGoalWeeklyRecordSingletonObject
import com.vn.wecare.feature.home.goal.data.GoalsRepository
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.GoalDailyRecord
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import com.vn.wecare.feature.home.goal.utils.getDayFromLongWithFormat
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

private const val CALORIES_IN_FIELD = "caloriesIn"
private const val CALORIES_OUT_FIELD = "caloriesOut"

class UpdateGoalRecordUsecase @Inject constructor(
    private val repository: GoalsRepository
) {
    suspend fun updateCaloriesInForCurrentDayRecord(caloriesIn: Int) {
        if (LatestGoalSingletonObject.getInStance().goalStatus != GoalStatus.INPROGRESS.value) return
        val currentDailyRecord = CurrentGoalDailyRecordSingletonObject.getInstance()
        if (currentDailyRecord == GoalDailyRecord()) {
            val record = GoalDailyRecord(
                day = getDayFromLongWithFormat(System.currentTimeMillis()), caloriesIn = caloriesIn
            )
            repository.insertGoalDailyRecord(record).collect()
            CurrentGoalDailyRecordSingletonObject.updateInstance(record)
        } else {
            val currentCalories = currentDailyRecord.caloriesIn + caloriesIn
            repository.updateCaloriesAmountForGoalDailyRecord(
                field = CALORIES_IN_FIELD,
                recordDay = currentDailyRecord.day,
                value = currentCalories
            ).collect()
            CurrentGoalDailyRecordSingletonObject.updateInstance(
                currentDailyRecord.copy(caloriesIn = currentCalories)
            )
        }
    }

    suspend fun updateCaloriesInForCurrentWeekRecord(caloriesIn: Int) {
        if (LatestGoalSingletonObject.getInStance().goalStatus != GoalStatus.INPROGRESS.value) return
        val currentWeeklyRecord = CurrentGoalWeeklyRecordSingletonObject.getInstance()
        val currentWeeklyCaloriesIn = currentWeeklyRecord.caloriesIn + caloriesIn

        repository.updateCaloriesAmountForGoalWeeklyRecord(
            CALORIES_IN_FIELD, currentWeeklyCaloriesIn
        ).collect()
        CurrentGoalWeeklyRecordSingletonObject.updateInstance(
            currentWeeklyRecord.copy(caloriesIn = currentWeeklyCaloriesIn)
        )
    }

    suspend fun updateCaloriesOutForCurrentDayRecord(caloriesOut: Int) {
        val currentDailyRecord = CurrentGoalDailyRecordSingletonObject.getInstance()
        if (currentDailyRecord == GoalDailyRecord()) {
            val record = GoalDailyRecord(
                day = getDayFromLongWithFormat(System.currentTimeMillis()), caloriesOut = caloriesOut
            )
            repository.insertGoalDailyRecord(record).collect()
            CurrentGoalDailyRecordSingletonObject.updateInstance(record)
        } else {
            val currentCalories = currentDailyRecord.caloriesOut + caloriesOut
            repository.updateCaloriesAmountForGoalDailyRecord(
                field = CALORIES_OUT_FIELD,
                recordDay = currentDailyRecord.day,
                value = currentCalories
            ).collect()
            CurrentGoalDailyRecordSingletonObject.updateInstance(
                currentDailyRecord.copy(caloriesOut = currentCalories)
            )
        }
    }

    suspend fun updateCaloriesOutForCurrentWeekRecord(caloriesOut: Int) {
        val currentWeeklyRecord = CurrentGoalWeeklyRecordSingletonObject.getInstance()
        val currentWeeklyCaloriesIn = currentWeeklyRecord.caloriesOut + caloriesOut

        repository.updateCaloriesAmountForGoalWeeklyRecord(
            CALORIES_OUT_FIELD, currentWeeklyCaloriesIn
        ).collect()
        CurrentGoalWeeklyRecordSingletonObject.updateInstance(
            currentWeeklyRecord.copy(caloriesOut = currentWeeklyCaloriesIn)
        )
    }
}
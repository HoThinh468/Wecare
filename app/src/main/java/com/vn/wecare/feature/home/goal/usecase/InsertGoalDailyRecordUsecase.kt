package com.vn.wecare.feature.home.goal.usecase

import com.vn.wecare.feature.home.goal.data.CurrentGoalDailyRecordSingletonObject
import com.vn.wecare.feature.home.goal.data.CurrentGoalWeeklyRecordSingletonObject
import com.vn.wecare.feature.home.goal.data.GoalsRepository
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.GoalDailyRecord
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.feature.home.goal.utils.getDayFromLongWithFormat
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class InsertGoalDailyRecordUsecase @Inject constructor(
    private val repo: GoalsRepository
) {
    suspend fun whenAddCaloriesOutRecord(
        caloriesIn: Int
    ) {
        val now = System.currentTimeMillis()
        val record = GoalDailyRecord(
            day = getDayFromLongWithFormat(now),
            dayInLong = now,
            caloriesOut = caloriesIn,
            goalDailyCalories = LatestGoalSingletonObject.getInStance().caloriesInEachDayGoal,
            goalDailyCaloriesOut = LatestGoalSingletonObject.getInStance().caloriesBurnedEachDayGoal
        )
        repo.apply {
            this.insertGoalDailyRecord(record).collect()
            this.updateInfoForCurrentGoalWeeklyRecord(
                field = GoalWeeklyRecord.numberOfDayRecordField,
                value = CurrentGoalWeeklyRecordSingletonObject.getInstance().numberOfDayRecord + 1
            ).collect()
            if (CurrentGoalWeeklyRecordSingletonObject.getInstance().status == GoalStatus.NOTSTARTED.value) {
                this.updateInfoForCurrentGoalWeeklyRecord(
                    field = GoalWeeklyRecord.statusField, value = GoalStatus.INPROGRESS.value
                ).collect()
            }
        }
        CurrentGoalDailyRecordSingletonObject.updateInstance(record)
    }

    suspend fun whenAddCaloriesInRecord(
        caloriesIn: Int, protein: Int, fat: Int, carbs: Int
    ) {
        val now = System.currentTimeMillis()
        val record = GoalDailyRecord(
            day = getDayFromLongWithFormat(now),
            dayInLong = now,
            caloriesIn = caloriesIn,
            proteinAmount = protein,
            fatAmount = fat,
            carbsAmount = carbs,
            goalDailyCalories = LatestGoalSingletonObject.getInStance().caloriesInEachDayGoal,
            goalDailyCaloriesOut = LatestGoalSingletonObject.getInStance().caloriesBurnedEachDayGoal
        )
        repo.apply {
            this.insertGoalDailyRecord(record).collect()
            this.updateInfoForCurrentGoalWeeklyRecord(
                field = GoalWeeklyRecord.numberOfDayRecordField,
                value = CurrentGoalWeeklyRecordSingletonObject.getInstance().numberOfDayRecord + 1
            ).collect()
            if (CurrentGoalWeeklyRecordSingletonObject.getInstance().status == GoalStatus.NOTSTARTED.value) {
                this.updateInfoForCurrentGoalWeeklyRecord(
                    field = GoalWeeklyRecord.statusField, value = GoalStatus.INPROGRESS.value
                ).collect()
            }
        }
        CurrentGoalDailyRecordSingletonObject.updateInstance(record)
    }
}
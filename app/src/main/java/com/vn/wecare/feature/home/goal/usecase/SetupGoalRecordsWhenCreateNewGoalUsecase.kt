package com.vn.wecare.feature.home.goal.usecase

import android.util.Log
import com.vn.wecare.feature.home.goal.data.CurrentGoalWeeklyRecordSingletonObject
import com.vn.wecare.feature.home.goal.data.GoalsRepository
import com.vn.wecare.feature.home.goal.data.model.GoalDailyRecord
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.feature.home.goal.utils.getListOfWeeklyRecordsWithCurrentTimeAndNumberOfWeek
import com.vn.wecare.feature.onboarding.OnboardingFragment
import com.vn.wecare.utils.WecareUserConstantValues
import com.vn.wecare.utils.getDayOfWeekInStringWithLong
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class SetupGoalRecordsWhenCreateNewGoalUsecase @Inject constructor(
    private val goalsRepository: GoalsRepository
) {
    suspend fun setup(
        numberOfWeek: Int,
        latestGoalId: String,
        weeklyGoalWeight: Float,
        weeklyGoalCalories: Int,
        weeklyGoalCaloriesOut: Int
    ) {
        val weeklyRecords = getListOfWeeklyRecordsWithCurrentTimeAndNumberOfWeek(
            System.currentTimeMillis(),
            numberOfWeek,
            weeklyGoalWeight,
            weeklyGoalCalories,
            weeklyGoalCaloriesOut
        )
        if (weeklyRecords.isEmpty()) return
        CurrentGoalWeeklyRecordSingletonObject.updateInstance(weeklyRecords.first())
        try {
            for (record in weeklyRecords) {
                goalsRepository.insertGoalWeeklyRecord(record, latestGoalId).collect()
            }
        } catch (e: Exception) {
            Log.d(OnboardingFragment.onboardingTag, "Setup goal fail due to ${e.message}")
        }
    }
}
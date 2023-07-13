package com.vn.wecare.feature.home.goal.usecase

import android.util.Log
import com.vn.wecare.feature.account.view.editinfo.EditInformationFragment
import com.vn.wecare.feature.home.goal.data.GoalsRepository
import com.vn.wecare.feature.home.goal.data.CurrentGoalWeeklyRecordSingletonObject
import com.vn.wecare.feature.home.goal.utils.getListOfWeeklyRecordsWithCurrentTimeAndNumberOfWeek
import com.vn.wecare.feature.onboarding.OnboardingFragment
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class SetupGoalWeeklyRecordsWhenCreateNewGoalUsecase @Inject constructor(
    private val goalsRepository: GoalsRepository
) {
    suspend fun setup(numberOfWeek: Int, latestGoalId: String) {
        val weeklyRecords = getListOfWeeklyRecordsWithCurrentTimeAndNumberOfWeek(
            System.currentTimeMillis(), numberOfWeek
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
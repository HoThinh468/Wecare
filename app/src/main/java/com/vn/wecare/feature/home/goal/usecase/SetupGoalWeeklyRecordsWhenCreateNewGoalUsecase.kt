package com.vn.wecare.feature.home.goal.usecase

import com.vn.wecare.feature.home.goal.data.GoalsRepository
import com.vn.wecare.feature.home.goal.data.CurrentGoalWeeklyRecordSingletonObject
import com.vn.wecare.feature.home.goal.utils.getListOfWeeklyRecordsWithCurrentTimeAndNumberOfWeek
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class SetupGoalWeeklyRecordsWhenCreateNewGoalUsecase @Inject constructor(
    private val goalsRepository: GoalsRepository
) {
    suspend operator fun invoke(numberOfWeek: Int, latestGoalId: String) {
        val weeklyRecords = getListOfWeeklyRecordsWithCurrentTimeAndNumberOfWeek(
            System.currentTimeMillis(), numberOfWeek
        )
        CurrentGoalWeeklyRecordSingletonObject.updateInstance(weeklyRecords.first())
        for (record in weeklyRecords) {
            goalsRepository.insertGoalWeeklyRecord(record, latestGoalId).collect()
        }
    }
}
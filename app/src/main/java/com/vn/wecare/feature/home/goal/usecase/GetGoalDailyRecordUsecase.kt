package com.vn.wecare.feature.home.goal.usecase

import com.vn.wecare.feature.goal.GoalsRepository
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.feature.home.goal.utils.generateGoalWeeklyRecordIdWithGoal
import javax.inject.Inject

class GetGoalDailyRecordUsecase @Inject constructor(
    private val goalsRepository: GoalsRepository
) {
    fun getCurrentGoalDailyRecord(goalId: String, weeklyRecord: GoalWeeklyRecord) = goalsRepository.getCurrentGoalDailyReport(
        goalId,
        generateGoalWeeklyRecordIdWithGoal(weeklyRecord)
    )
}
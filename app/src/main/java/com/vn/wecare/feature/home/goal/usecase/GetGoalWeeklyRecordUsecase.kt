package com.vn.wecare.feature.home.goal.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.goal.data.GoalsRepository
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGoalWeeklyRecordUsecase @Inject constructor(
    private val goalsRepository: GoalsRepository
) {
    fun getCurrentGoalWeeklyRecord(goalId: String): Flow<Response<GoalWeeklyRecord>> {
        return goalsRepository.getCurrentWeeklyRecord(goalId)
    }

    fun getAllRecord(goalId: String) = goalsRepository.getAllWeeklyRecordsOfAGoal(goalId)
}
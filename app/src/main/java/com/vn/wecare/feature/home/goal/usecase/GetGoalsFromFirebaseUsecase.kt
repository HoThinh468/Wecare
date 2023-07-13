package com.vn.wecare.feature.home.goal.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.goal.data.GoalsRepository
import com.vn.wecare.feature.home.goal.data.model.Goal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGoalsFromFirebaseUsecase @Inject constructor(private val goalsRepository: GoalsRepository) {
    fun getGoalsFromFirebase(): Flow<Response<List<Goal>>> {
        return goalsRepository.getGoals()
    }

    fun getCurrentGoalFromFirebase(): Flow<Response<Goal>> {
        return goalsRepository.getLatestGoal()
    }
}
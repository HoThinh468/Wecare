package com.vn.wecare.feature.home.goal.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.goal.GoalsRepository
import com.vn.wecare.feature.home.goal.data.model.Goal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveGoalsToFirebaseUsecase @Inject constructor(
    private val goalsRepository: GoalsRepository
) {
    fun saveGoalsToFirebase(goal: Goal): Flow<Response<Boolean>> = goalsRepository.insertGoal(goal)
}
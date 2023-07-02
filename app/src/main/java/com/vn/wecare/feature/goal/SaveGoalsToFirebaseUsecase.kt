package com.vn.wecare.feature.goal

import com.vn.wecare.core.data.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveGoalsToFirebaseUsecase @Inject constructor(
    private val goalsRepository: GoalsRepository
) {
    fun saveGoalsToFirebase(goal: Goal): Flow<Response<Boolean>> = goalsRepository.insertGoal(goal)
}
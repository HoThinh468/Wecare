package com.vn.wecare.feature.goal

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGoalsFromFirebaseUsecase @Inject constructor(private val goalsRepository: GoalsRepository) {
    fun getGoalsFromFirebase(userId: String): Flow<Goal> {
        return goalsRepository.getGoalsWithUserId(userId)
    }
}
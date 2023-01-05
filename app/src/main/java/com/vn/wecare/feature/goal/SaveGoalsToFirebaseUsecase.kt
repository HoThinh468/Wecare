package com.vn.wecare.feature.goal

import javax.inject.Inject

class SaveGoalsToFirebaseUsecase @Inject constructor(
    private val goalsRepository: GoalsRepository
) {
    fun saveGoalsToFirebase(userId: String, stepsGoals: Int) {
        val goals =
            Goals(userId, stepsGoals, (stepsGoals * 0.04).toInt(), (stepsGoals * 0.01).toInt())
        goalsRepository.insertGoal(goals)
    }
}
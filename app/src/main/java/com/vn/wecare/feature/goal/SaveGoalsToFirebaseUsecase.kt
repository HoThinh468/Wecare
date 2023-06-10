package com.vn.wecare.feature.goal

import javax.inject.Inject

class SaveGoalsToFirebaseUsecase @Inject constructor(
    private val goalsRepository: GoalsRepository
) {
    fun saveGoalsToFirebase(userId: String, stepsGoals: Int, caloriesGoal: Int, moveTimeGoal: Int) {
        val goals =
            Goals(userId, stepsGoals, caloriesGoal, moveTimeGoal)
        goalsRepository.insertGoal(goals)
    }
}
package com.vn.wecare.feature.training.dashboard.usecase

import com.vn.wecare.feature.training.TrainingHistoryRepo

class GetWeeklyCheck (
    private val repo: TrainingHistoryRepo
) {
    suspend operator fun invoke(
    ) = repo.getWeeklyCheck()
}
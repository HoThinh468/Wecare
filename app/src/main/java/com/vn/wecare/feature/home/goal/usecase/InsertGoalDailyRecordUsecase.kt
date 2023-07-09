package com.vn.wecare.feature.home.goal.usecase

import com.vn.wecare.feature.goal.GoalsRepository
import com.vn.wecare.feature.home.goal.data.model.GoalDailyRecord
import javax.inject.Inject

class InsertGoalDailyRecordUsecase @Inject constructor(
    private val goalsRepository: GoalsRepository
) {
    operator fun invoke(record: GoalDailyRecord) = goalsRepository.insertGoalDailyRecord(record)
}
package com.vn.wecare.feature.home.step_count.usecase

import com.vn.wecare.feature.authentication.ui.service.AccountService
import com.vn.wecare.feature.home.step_count.data.model.StepsPerDay
import com.vn.wecare.feature.home.step_count.data.repository.StepsPerDayRepository
import com.vn.wecare.utils.getCurrentDayId
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SaveStepsPerDayToFirestoreUsecase @Inject constructor(
    private val stepsPerDayRepository: StepsPerDayRepository,
    private val accountService: AccountService
) {
    suspend fun saveStepsPerDayToFirestore(steps: Float) {
        val stepsPerDay = StepsPerDay(
            getCurrentDayId(),
            accountService.currentUserId,
            steps.toInt(),
            (steps * 0.04).toInt(),
            (steps * 0.01).toInt()
        )
        coroutineScope {
            launch { stepsPerDayRepository.insertStepsPerDayToFirestore(stepsPerDay) }
        }
    }
}
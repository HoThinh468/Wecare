package com.vn.wecare.feature.home.step_count.usecase

import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import com.vn.wecare.feature.home.step_count.data.repository.StepsPerHoursRepository
import com.vn.wecare.utils.getCurrentDayId
import com.vn.wecare.utils.getCurrentHourId
import javax.inject.Inject

class SaveStepsPerHourUsecase @Inject constructor(
    private val stepsPerHoursRepository: StepsPerHoursRepository
) {
    suspend fun insertStepsPerHourToDb(steps: Float) {
        val stepsPerHour = StepsPerHour(
            getCurrentHourId(),
            getCurrentDayId(),
            steps.toInt(),
            (steps * 0.05).toInt(),
            0
        )
        stepsPerHoursRepository.insert(stepsPerHour)
    }
}
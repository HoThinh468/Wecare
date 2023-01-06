package com.vn.wecare.feature.home.step_count.usecase

import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayWithHours
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import com.vn.wecare.feature.home.step_count.data.repository.StepsPerHoursRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStepsPerDayWithHoursUsecase @Inject constructor(
    private val stepsPerHoursRepository: StepsPerHoursRepository
) {
    fun getStepsPerDayWithHour(dayId: String): Flow<List<StepsPerHourEntity?>> {
        return stepsPerHoursRepository.getStepsPerDayWithHours(dayId)
    }
}
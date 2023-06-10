package com.vn.wecare.feature.home.step_count.usecase

import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import com.vn.wecare.feature.home.step_count.data.repository.StepsPerHoursRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStepsPerHourWithDayIdUsecase @Inject constructor(
    private val stepsPerHoursRepository: StepsPerHoursRepository
) {
    fun getStepsPerHourWithDayId(dayId: String): Flow<List<StepsPerHourEntity?>> {
        return stepsPerHoursRepository.getStepsPerHourWithDayId(dayId)
    }

    fun getStepsPerHoursFromRemoteWithDayId(dayId: String): Flow<List<StepsPerHour?>> {
        return stepsPerHoursRepository.getStepsPerHoursFromRemoteWithDayId(dayId)
    }
}
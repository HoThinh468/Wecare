package com.vn.wecare.feature.home.step_count.usecase

import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import com.vn.wecare.feature.home.step_count.data.repository.StepsPerHoursRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetStepsPerHourUsecase @Inject constructor(
    private val stepsPerHoursRepository: StepsPerHoursRepository
) {
    fun getTotalStepsInDayWithDayId(dayId: String): Flow<Float> =
        stepsPerHoursRepository.getStepsPerHourWithDayId(dayId).map {
            var stepsInDay = 0f
            it.forEach { i ->
                if (i != null) {
                    stepsInDay += i.steps
                }
            }
            stepsInDay
        }

    private fun getTotalStepsFromStepsPerHourList(list: List<StepsPerHourEntity?>): Float {
        var stepsInDay = 0f
        list.forEach { it ->
            if (it != null) {
                stepsInDay += it.steps
            }
        }
        return stepsInDay
    }

    fun getStepsPerHours(dayId: String): Flow<List<StepsPerHour?>> {
        return stepsPerHoursRepository.getStepsPerHoursFromRemoteWithDayId(dayId)
    }

    fun getStepsPerHourWithHourId(hourId: String): Flow<StepsPerHourEntity?> {
        return stepsPerHoursRepository.getStepsPerHourWithHourId(hourId)
    }
}
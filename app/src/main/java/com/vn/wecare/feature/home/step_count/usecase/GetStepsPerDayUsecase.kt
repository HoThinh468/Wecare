package com.vn.wecare.feature.home.step_count.usecase

import com.vn.wecare.core.WecareSharePreferences
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity
import com.vn.wecare.feature.home.step_count.data.repository.StepsPerDayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

const val PREVIOUS_TOTAL_SENSOR_STEPS = "previous_total_sensor_steps"

class GetStepsPerDayUsecase @Inject constructor(
    private val sharedPreferences: WecareSharePreferences,
    private val stepsPerDayRepository: StepsPerDayRepository
) {
    fun getCurrentDaySteps(currentStepsFromSensor: Float): Flow<Float> = flow {
        // Get the currentStepsFromSensor and minus it with previousToTalStepsSaveInSharedPref
        val result = currentStepsFromSensor - sharedPreferences.getStepCountSharedPref()
            .getFloat(PREVIOUS_TOTAL_SENSOR_STEPS, 0f)
        emit(result)
    }

    fun getStepsPerDayWithDayId(dayId: String): Flow<StepsPerDayEntity?> {
        return stepsPerDayRepository.getStepsPerDay(dayId)
    }
}
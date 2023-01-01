package com.vn.wecare.feature.home.step_count.usecase

import android.content.SharedPreferences
import com.vn.wecare.feature.home.step_count.di.StepCountSharePref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

const val PREVIOUS_TOTAL_SENSOR_STEPS = "previous_total_sensor_steps"

class GetStepsPerDayUsecase @Inject constructor(
    @StepCountSharePref private val sharedPreferences: SharedPreferences,
) {
    fun getCurrentDaySteps(currentStepsFromSensor: Float): Flow<Float> = flow {
        // Get the currentStepsFromSensor and minus it with previousToTalStepsSaveInSharedPref
        val result =
            currentStepsFromSensor - sharedPreferences.getFloat(PREVIOUS_TOTAL_SENSOR_STEPS, 0f)
        emit(result)
    }
}
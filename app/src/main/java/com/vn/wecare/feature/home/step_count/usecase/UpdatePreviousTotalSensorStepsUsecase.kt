package com.vn.wecare.feature.home.step_count.usecase

import com.vn.wecare.core.WecareSharePreferences
import javax.inject.Inject

class UpdatePreviousTotalSensorStepsUsecase @Inject constructor(
    private val sharedPreferences: WecareSharePreferences
) {
    fun updatePreviousTotalSensorStepCount(sensorSteps: Float) {
        with(sharedPreferences.getStepCountSharedPref().edit()) {
            putFloat(PREVIOUS_TOTAL_SENSOR_STEPS, sensorSteps)
            apply()
        }
    }
}
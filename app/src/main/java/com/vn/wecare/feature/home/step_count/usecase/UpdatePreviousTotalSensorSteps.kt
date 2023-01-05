package com.vn.wecare.feature.home.step_count.usecase

import android.content.SharedPreferences
import com.vn.wecare.feature.home.step_count.di.StepCountSharePref
import javax.inject.Inject

class UpdatePreviousTotalSensorSteps @Inject constructor(
    @StepCountSharePref private val sharedPreferences: SharedPreferences,
){
    fun updatePreviousTotalSensorStepCount(sensorSteps: Float) {
        with (sharedPreferences.edit()) {
            putFloat(PREVIOUS_TOTAL_SENSOR_STEPS, sensorSteps)
            apply()
        }
    }
}
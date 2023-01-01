package com.vn.wecare.feature.home.step_count.usecase

import android.content.SharedPreferences
import com.vn.wecare.feature.home.step_count.di.StepCountSharePref
import javax.inject.Inject

const val LATEST_STEPS_COUNT = "latest_steps_count"

class GetCurrentStepsFromSensorUsecase @Inject constructor(
    @StepCountSharePref private val sharedPreferences: SharedPreferences,
) {
    fun getCurrentStepsFromSensor() : Float {
        return sharedPreferences.getFloat(LATEST_STEPS_COUNT, 0f)
    }
}
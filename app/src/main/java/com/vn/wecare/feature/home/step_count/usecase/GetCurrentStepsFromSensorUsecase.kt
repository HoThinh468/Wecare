package com.vn.wecare.feature.home.step_count.usecase

import android.content.SharedPreferences
import android.util.Log
import com.vn.wecare.feature.home.step_count.di.StepCountSharePref
import javax.inject.Inject

const val LATEST_STEPS_COUNT = "latest_steps_count"

class GetCurrentStepsFromSensorUsecase @Inject constructor(
    @StepCountSharePref private val sharedPreferences: SharedPreferences,
) {
    fun getCurrentStepsFromSensor(): Float {
        Log.d("latest_steps: ", sharedPreferences.getFloat(LATEST_STEPS_COUNT, 0f).toString())
        return sharedPreferences.getFloat(LATEST_STEPS_COUNT, 0f)
    }
}
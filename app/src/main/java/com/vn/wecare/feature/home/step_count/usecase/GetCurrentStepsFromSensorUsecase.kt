package com.vn.wecare.feature.home.step_count.usecase

import com.vn.wecare.core.WecareSharePreferences
import javax.inject.Inject

const val CURRENT_STEP_FROM_SENSOR = "latest_steps_count"

class GetCurrentStepsFromSensorUsecase @Inject constructor(
    private val sharedPref: WecareSharePreferences
) {
    fun getCurrentStepsFromSensor(): Float =
        sharedPref.getStepCountSharedPref().getFloat(CURRENT_STEP_FROM_SENSOR, 0f)
}
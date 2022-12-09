package com.vn.wecare.feature.home.step_count

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import com.vn.wecare.feature.home.step_count.di.StepCountSharePref
import com.vn.wecare.feature.home.step_count.ui.compose.MotionSensor
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

const val PREVIOUS_DAY_STEPS = "previous_day_steps"
const val PREVIOUS_HOUR_STEPS = "previous_hour_steps"

class StepCountCalculateUsecase @Inject constructor(
    private val motionSensor: MotionSensor,
    @StepCountSharePref private val sharedPreferences: SharedPreferences
) {

    // Store total steps of the previous day
    private var previousDayTotalSteps =
        mutableStateOf(sharedPreferences.getFloat(PREVIOUS_DAY_STEPS, 0f))
    private var previousHourTotalSteps = mutableStateOf(
        sharedPreferences.getFloat(
            PREVIOUS_HOUR_STEPS, 0f
        )
    )

    // Store total steps get from sensor
    private var totalStepsFromSensor = mutableStateOf(0f)

    // Store current steps in a day
    var currentSteps = MutableStateFlow(0f)

    private fun updateStepsFromSensor() {
//        if (motionSensor.isMotionSensorExist()) {
//            totalStepsFromSensor.value = motionSensor.getCurrentStepsFromSensor()
//        }
        totalStepsFromSensor.value = motionSensor.currentSteps.value
    }

    fun updatePreviousTotalStepsWhenUserFirstSignUp() {
//        previousDayTotalSteps.value = motionSensor.getCurrentStepsFromSensor()
    }

    fun getCurrentSteps(): Float {
        updateStepsFromSensor()
        currentSteps.value = totalStepsFromSensor.value.minus(previousDayTotalSteps.value)
        return currentSteps.value
    }
}
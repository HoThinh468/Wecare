package com.vn.wecare.feature.home.step_count

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import com.vn.wecare.feature.home.step_count.data.repository.StepsPerHoursRepository
import com.vn.wecare.feature.home.step_count.di.StepCountSharePref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

const val PREVIOUS_DAY_STEPS = "previous_day_steps"
const val PREVIOUS_HOUR_STEPS = "previous_hour_steps"
const val LATEST_STEPS_COUNT = "latest_steps_count"
const val LATEST_CALORIES = "latest_calories"

class StepCountUsecase @Inject constructor(
    @StepCountSharePref private val sharedPreferences: SharedPreferences,
    private val stepsPerHoursRepository: StepsPerHoursRepository
) {

    // Store total steps of the previous day
    private var previousDayTotalSteps =
        mutableStateOf(sharedPreferences.getFloat(PREVIOUS_DAY_STEPS, 0f))

    private var previousHourTotalSteps = mutableStateOf(
        sharedPreferences.getFloat(
            PREVIOUS_HOUR_STEPS, 0f
        )
    )

    // Store current steps in day
    private var currentSteps = MutableStateFlow(0f)

    // Store calories consumed a day
    private var caloriesBurnedRecord = MutableStateFlow(0f)

    private var moveMin = MutableStateFlow(0f)

    fun calculateCurrentDaySteps(stepsFromSensor: Float): Flow<Float> = flow {
        val temp = stepsFromSensor - previousDayTotalSteps.value
        currentSteps.update { temp }
        emit(temp)
    }

    fun calculateCurrentCaloriesConsumed(): Flow<Float> = flow {
        caloriesBurnedRecord.update { (currentSteps.value * 0.05).toFloat() }
        emit(caloriesBurnedRecord.value)
    }

    fun updateSharedPref() {
        with (sharedPreferences.edit()) {
            putFloat(LATEST_STEPS_COUNT, currentSteps.value)
            putFloat(LATEST_CALORIES, caloriesBurnedRecord.value)
        }
    }
}
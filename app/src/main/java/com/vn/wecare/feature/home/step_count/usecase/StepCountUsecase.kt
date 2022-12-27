package com.vn.wecare.feature.home.step_count.usecase

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import com.vn.wecare.feature.home.step_count.data.model.StepsPerDay
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import com.vn.wecare.feature.home.step_count.data.repository.StepsPerDayRepository
import com.vn.wecare.feature.home.step_count.data.repository.StepsPerHoursRepository
import com.vn.wecare.feature.home.step_count.di.StepCountSharePref
import com.vn.wecare.utils.getCurrentDayId
import com.vn.wecare.utils.getCurrentHourId
import kotlinx.coroutines.flow.*
import javax.inject.Inject

const val PREVIOUS_TOTAL_SENSOR_STEPS = "previous_total_sensor_steps"
const val LATEST_STEPS_COUNT = "latest_steps_count"

class StepCountUsecase @Inject constructor(
    @StepCountSharePref private val sharedPreferences: SharedPreferences,
    private val stepsPerHoursRepository: StepsPerHoursRepository,
    private val stepsPerDayRepository: StepsPerDayRepository
) {

    // Store current steps in day
    private var currentSteps = MutableStateFlow(0f)

    // Store calories consumed a day
    private var caloriesBurnedRecord = MutableStateFlow(0f)

    private var moveMin = MutableStateFlow(0)

    // Call at the end of the day by broad cast receiver to store
    fun updatePreviousTotalSensorStepCount(sensorSteps: Float) {
        with (sharedPreferences.edit()) {
            putFloat(PREVIOUS_TOTAL_SENSOR_STEPS, sensorSteps)
            apply()
        }
    }

    fun calculateCurrentDaySteps(stepsFromSensor: Float): Flow<Float> = flow {
        val temp = stepsFromSensor - sharedPreferences.getFloat(PREVIOUS_TOTAL_SENSOR_STEPS, 0f)
        currentSteps.update { temp }

        emit(temp)
    }

    fun calculateCurrentCaloriesConsumed(): Flow<Float> = flow {
        caloriesBurnedRecord.update { (currentSteps.value * 0.05).toFloat() }
        emit(caloriesBurnedRecord.value)
    }

    private fun getTotalStepsInADayUntilPreviousHour(dayId: String): Float {
        var sum = 0f
        stepsPerHoursRepository.getStepsPerDayWithHours(dayId)?.map {
            it.forEach() { stepsPerDayWithHours ->
                sum += stepsPerDayWithHours.hour.steps
            }
        }?.catch {}
        return sum
    }

    fun getCurrentHourSteps(currentDaySteps: Float): Float {
        return currentDaySteps - getTotalStepsInADayUntilPreviousHour(getCurrentDayId())
    }

    suspend fun insertStepsPerHourToDb(steps: Float) {
        val stepsPerHour = StepsPerHour(
            getCurrentHourId(),
            getCurrentDayId(),
            steps.toInt(),
            caloriesBurnedRecord.value.toInt(),
            0
        )
        stepsPerHoursRepository.insert(stepsPerHour)
    }

    suspend fun insertStepsPerDayToDb(steps: Float) {
        val stepsPerDay = StepsPerDay(
            getCurrentDayId(),
            "abcds",
            currentSteps.value.toInt(),
            caloriesBurnedRecord.value.toInt(),
            moveMin.value
        )
        stepsPerDayRepository.insertStepsPerDay(stepsPerDay)
    }

    fun getSharedPrefLatestStep(): Float {
        return sharedPreferences.getFloat(LATEST_STEPS_COUNT, 0f)
    }
}
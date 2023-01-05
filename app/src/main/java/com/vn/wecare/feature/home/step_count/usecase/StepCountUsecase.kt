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


class StepCountUsecase @Inject constructor(
    @StepCountSharePref private val sharedPreferences: SharedPreferences,
    private val stepsPerDayRepository: StepsPerDayRepository
) {

    // Store current steps in day
    private var currentSteps = MutableStateFlow(0f)

    // Store calories consumed a day
    private var caloriesBurnedRecord = MutableStateFlow(0f)

    private var moveMin = MutableStateFlow(0)

//    fun calculateCurrentDaySteps(stepsFromSensor: Float): Flow<Float> = flow {
//        val temp = stepsFromSensor - sharedPreferences.getFloat(PREVIOUS_TOTAL_SENSOR_STEPS, 0f)
//        currentSteps.update { temp }
//
//        emit(temp)
//    }

    fun calculateCurrentCaloriesConsumed(): Flow<Float> = flow {
        caloriesBurnedRecord.update { (currentSteps.value * 0.05).toFloat() }
        emit(caloriesBurnedRecord.value)
    }
}
package com.vn.wecare.feature.exercises.workout_dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.WecareUserSingletonObject
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.ext.toDD_MM_yyyy
import com.vn.wecare.feature.home.HomeUiState
import com.vn.wecare.feature.home.step_count.data.model.StepsPerDay
import com.vn.wecare.feature.home.step_count.getCaloriesBurnedFromStepCount
import com.vn.wecare.feature.home.step_count.getMoveTimeFromStepCount
import com.vn.wecare.feature.home.step_count.usecase.CaloPerDay
import com.vn.wecare.feature.home.step_count.usecase.DashboardUseCase
import com.vn.wecare.feature.home.step_count.usecase.GetCurrentStepsFromSensorUsecase
import com.vn.wecare.feature.home.step_count.usecase.GetStepsPerDayUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ExerciseDashboardViewModel @Inject constructor(
    private val getStepsPerDayUsecase: GetStepsPerDayUsecase,
    private val dashboardUseCase: DashboardUseCase,
    private val getCurrentStepsFromSensorUsecase: GetCurrentStepsFromSensorUsecase,
) : ViewModel() {

    init {
        dashboardUseCase.getCaloPerDay()
        getCaloPerDay()
        updateCurrentSteps(getCurrentStepsFromSensorUsecase.getCurrentStepsFromSensor())
    }

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    var updateStepsResponse by mutableStateOf<Response<Boolean>>(Response.Loading)

    var getCaloPerDayResponse by mutableStateOf<Response<CaloPerDay?>>(Response.Loading)
    private val _caloPerDay = MutableStateFlow<CaloPerDay?>(CaloPerDay())
    val caloPerDay: StateFlow<CaloPerDay?>
        get() = _caloPerDay

    fun updateCurrentSteps(stepsFromSensor: Float) = viewModelScope.launch {
        val info = WecareUserSingletonObject.getInstance()
        getStepsPerDayUsecase.getStepsInPreviousDay(
            StepsPerDay(
                dayId = System.currentTimeMillis().minus(86400000L).toDD_MM_yyyy(),
                steps = stepsFromSensor.toInt(),
                calories = stepsFromSensor.toInt().getCaloriesBurnedFromStepCount(info.height ?: 1, info.weight ?: 1),
                moveTime = stepsFromSensor.toInt().getMoveTimeFromStepCount(info.height ?: 1)
            )
        ).collect { response ->
            if (response is Response.Success) {
                val stepsInPreviousDay = response.data
                val steps = stepsFromSensor.toInt() - stepsInPreviousDay.steps
                val caloriesBurned = steps.getCaloriesBurnedFromStepCount(info.height ?: 1, info.weight ?: 1)
                val moveTime = steps.getMoveTimeFromStepCount(info.height ?: 1)

                getStepsPerDayUsecase.updateStepsPerDay(
                    StepsPerDay(
                        dayId = System.currentTimeMillis().toDD_MM_yyyy(),
                        steps = steps,
                        calories = caloriesBurned,
                        moveTime = moveTime
                    )
                ).collect { response ->
                    updateStepsResponse = response
                }

                dashboardUseCase.updateCaloPerDay(
                    CaloPerDay(
                        caloOutWalking = caloriesBurned
                    )
                )

                _homeUiState.update {
                    if(stepsFromSensor == 0f) {
                        it.copy(
                            stepCount = 0,
                            caloriesBurnt = 0,
                            timeConsumed = 0
                        )
                    } else {
                        it.copy(
                            stepCount = steps,
                            caloriesBurnt = caloriesBurned,
                            timeConsumed = moveTime
                        )
                    }
                }
            }
        }
    }

     fun getCaloPerDay() = viewModelScope.launch {
        dashboardUseCase.getCaloPerDay().collect { response ->
            getCaloPerDayResponse = response
            if (response is Response.Success) {
                _caloPerDay.emit(response.data)
            }
        }
    }
}
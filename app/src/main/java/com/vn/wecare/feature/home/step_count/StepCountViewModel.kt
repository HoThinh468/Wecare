package com.vn.wecare.feature.home.step_count

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.feature.home.step_count.data.entity.toModel
import com.vn.wecare.feature.home.step_count.usecase.GetCurrentStepsFromSensorUsecase
import com.vn.wecare.feature.home.step_count.usecase.GetStepsPerDayUsecase
import com.vn.wecare.utils.getCurrentDayId
import com.vn.wecare.utils.getDayId
import com.vn.wecare.utils.getMonthPrefix
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

/**
 * Define a model for view presentation
 */
data class StepsCountUiState(
    val currentSteps: Int = 0,
    val caloConsumed: Int = 0,
    val moveMin: Int = 0,
    val isLoading: Boolean = false,
    val selectedDay: String = "",
    val hasData: Boolean = true
)

@HiltViewModel
class StepCountViewModel @Inject constructor(
    private val getCurrentStepsFromSensorUsecase: GetCurrentStepsFromSensorUsecase,
    private val getStepsPerDayUsecase: GetStepsPerDayUsecase,
) : ViewModel() {

    // Define a variable of ui state
    private val _stepsCountUiState = MutableStateFlow(StepsCountUiState())
    val stepsCountUiState: StateFlow<StepsCountUiState> get() = _stepsCountUiState

    init {
        val currentDate = LocalDate.now()
        updateCurrentSteps(getCurrentStepsFromSensorUsecase.getCurrentStepsFromSensor())
        updateDateTitle(currentDate.dayOfMonth, currentDate.monthValue, currentDate.year)
    }

    fun updateCurrentSteps(stepsFromSensor: Float) = viewModelScope.launch {
        getStepsPerDayUsecase.getCurrentDaySteps(stepsFromSensor).collect { steps ->
            _stepsCountUiState.update {
                it.copy(
                    currentSteps = steps.toInt(),
                    caloConsumed = (steps * 0.04).toInt(),
                    moveMin = (steps * 0.01).toInt(),
                    hasData = true
                )
            }
        }
    }

    fun onDaySelected(year: Int, month: Int, dayOfMonth: Int) {
        updateDateTitle(dayOfMonth, month, year)
        if (getCurrentDayId() == getDayId(dayOfMonth, month, year)) {
            updateCurrentSteps(getCurrentStepsFromSensorUsecase.getCurrentStepsFromSensor())
        } else {
            viewModelScope.launch {
                getStepsPerDayUsecase.getStepsPerDayWithDayId(getDayId(dayOfMonth, month, year))
                    .collect { stepsPerDay ->
                        if (stepsPerDay != null) {
                            _stepsCountUiState.update { ui ->
                                ui.copy(
                                    currentSteps = stepsPerDay.steps,
                                    caloConsumed = stepsPerDay.toModel().calories,
                                    moveMin = stepsPerDay.toModel().moveTime,
                                    hasData = true
                                )
                            }
                        } else {
                            _stepsCountUiState.update {
                                it.copy(hasData = false)
                            }
                        }
                    }
            }
        }
    }

    private fun updateDateTitle(day: Int, month: Int, year: Int) {
        _stepsCountUiState.update {
            it.copy(
                selectedDay = "${getMonthPrefix(month)} $day, $year"
            )
        }
    }
}
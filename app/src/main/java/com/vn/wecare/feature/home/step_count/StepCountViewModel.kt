package com.vn.wecare.feature.home.step_count

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.feature.home.step_count.di.StepCountSharePref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Define a model for view presentation
 */
data class StepsCountUiState(
    val currentSteps: Int = 0,
    val caloConsumed: Int = 0,
    val moveMin: Int = 0,
    val isPermissionEnable: Boolean = false, // Check permission to avoid crash
    val isLoading: Boolean = false,
)

@HiltViewModel
class StepCountViewModel @Inject constructor(
    private val stepCountUsecase: StepCountUsecase,
    @StepCountSharePref private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    // Define a variable of ui state
    private val _stepsCountUiState = MutableStateFlow(StepsCountUiState())
    val stepsCountUiState: StateFlow<StepsCountUiState> get() = _stepsCountUiState

    init {
        updateCurrentSteps(sharedPreferences.getFloat(LATEST_STEPS_COUNT, 0f))
        updateCaloriesConsumed()
    }

    fun updateCurrentSteps(steps: Float) = viewModelScope.launch {
        stepCountUsecase.calculateCurrentDaySteps(steps).collect { steps ->
            _stepsCountUiState.update {
                it.copy(currentSteps = steps.toInt())
            }
        }
    }

    fun updateCaloriesConsumed() = viewModelScope.launch {
        stepCountUsecase.calculateCurrentCaloriesConsumed().collect { calories ->
            _stepsCountUiState.update {
                it.copy(
                    caloConsumed = calories.toInt()
                )
            }
        }
    }

    fun updateSharedPref() = stepCountUsecase.updateSharedPref()
}
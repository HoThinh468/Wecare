package com.vn.wecare.feature.home.step_count

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.feature.home.step_count.usecase.StepCountUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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
) : ViewModel() {

    // Define a variable of ui state
    private val _stepsCountUiState = MutableStateFlow(StepsCountUiState())
    val stepsCountUiState: StateFlow<StepsCountUiState> get() = _stepsCountUiState

    init {
        updateCurrentSteps(stepCountUsecase.getSharedPrefLatestStep())
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
//        stepCountUsecase.calculateCurrentCaloriesConsumed().collect { calories ->
//            _stepsCountUiState.update {
//                it.copy(
//                    caloConsumed = calories.toInt()
//                )
//            }
//        }
    }
}
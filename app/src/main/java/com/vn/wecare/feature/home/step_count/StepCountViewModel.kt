package com.vn.wecare.feature.home.step_count

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import com.vn.wecare.feature.home.step_count.data.repository.StepsPerHoursRepository
import com.vn.wecare.utils.getCurrentDayId
import com.vn.wecare.utils.getCurrentHourId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Define a model for view presentation
 */
data class StepsCountUiState(
    val currentSteps: Float = 0f,
    val caloConsumed: Int = 0,
    val moveMin: Int = 0,
    val isPermissionEnable: Boolean = false, // Check permission to avoid crash
)

@HiltViewModel
class StepCountViewModel @Inject constructor(
    private val stepCountCalculateUsecase: StepCountCalculateUsecase
) : ViewModel() {

    // Define a variable of ui state
    var stepsCountUiState = MutableStateFlow(StepsCountUiState())
        private set

    fun updateCurrentStepUI() {
        stepsCountUiState.update {
            it.copy(currentSteps = stepCountCalculateUsecase.getCurrentSteps())
        }
    }

    // store total steps of the sensor
//    private var totalSteps = mutableStateOf(0f)

    // store the last total steps when saving current steps to db
//    private var previousTotalSteps = mutableStateOf(0f)

    // Get total steps from motion sensor
//    private fun updateTotalSteps(steps: Float) {
//        totalSteps.value = steps
//    }

    // Call this function daily at 24:00 to save data to db
//    private fun updatePreviousTotalSteps() {
//        previousTotalSteps.value = totalSteps.value
//    }

//    fun calculateCurrentSteps(steps: Float) {
//        updateTotalSteps(steps)
//
//        stepsCountUiState.update {
//            it.copy(
//                currentSteps = totalSteps.value.minus(steps).toInt()
//            )
//        }
//    }

    /**
     * Insert new stepsPerHour to db each hour
     */
//    suspend fun insertNewHour() {
//        val newHour = StepsPerHour(
//            getCurrentHourId(),
//            getCurrentDayId(),
//            stepsCountUiState.value.currentSteps,
//            stepsCountUiState.value.caloConsumed,
//            stepsCountUiState.value.moveMin
//        )
//        stepsPerHoursRepository.insert(newHour)
//    }
}
package com.vn.wecare.feature.home.step_count.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.vn.wecare.feature.home.step_count.data.repository.StepsPerHourRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.roundToInt

data class StepsCountUiState(
    val currentSteps: Int = 0,
    val caloConsumed: Int = 0,
    val moveMin: Int = 0,
    val isPermissionEnable: Boolean = false, // Check permission to avoid crash
)

class StepCountViewModel(
    private val stepsCountRepository: StepsPerHourRepository
) : ViewModel() {

    var stepsCountUiState = MutableStateFlow(StepsCountUiState())
        private set

    private var totalSteps = mutableStateOf(0f)

    private var previousTotalSteps = mutableStateOf(0f)

    private var stepsPerHour = HashMap<Int, Int>(24)

    /**
     * Update total steps
     * TODO Need to save the value to dbs to avoid phone reboot
     */
    private fun updateTotalSteps(steps: Float) {
        totalSteps.value = steps
    }

    /**
     * Update previous total steps of the previous day
     */
    private fun updatePreviousTotalSteps() {
        previousTotalSteps.value = totalSteps.value
    }

    /**
     * Calculate current steps by taking the difference of the current total steps and previous total steps
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateCurrentCurrentSteps(steps: Float) {
        updateTotalSteps(steps)

        stepsCountUiState.update {
            it.copy(
                currentSteps = totalSteps.value.minus(previousTotalSteps.value).toInt()
            )
        }
    }

    /**
     * Calculate calories consumed through walking activity
     */
    fun calculateCaloConsumed() {
        stepsCountUiState.update {
            it.copy(
                caloConsumed = (stepsCountUiState.value.currentSteps * 0.4).roundToInt()
            )
        }
    }

    /**
     * Update permission status
     */
    fun updatePermissionStatus(newVal: Boolean) {
        stepsCountUiState.update {
            it.copy(
                isPermissionEnable = newVal
            )
        }
    }

    /**
     * Update steps of every hour to the HashMap<H, S>
     */
    fun updateStepPerHour(currentHour: Int) {
        if (currentHour == 0) {
            stepsPerHour[currentHour] = stepsCountUiState.value.currentSteps
        } else {
            var totalStepsInMap = 0
            for (i in stepsPerHour) {
                totalStepsInMap += i.value
            }
            stepsPerHour[currentHour] = stepsCountUiState.value.currentSteps - totalStepsInMap
        }
    }
}
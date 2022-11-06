package com.vn.wecare.feature.home.step_count

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime


data class StepsCountUiState(
    val currentSteps: Int = 0,
    val caloConsumed: Int = 0,
    val moveMin: Int = 0,
    val isPermissionEnable: Boolean = false, // Check permission to avoid crash
)

class StepCountViewModel : ViewModel() {

    var stepsCountUiState = MutableStateFlow(StepsCountUiState())
        private set

    private var totalSteps = mutableStateOf(0f)

    private var previousTotalSteps = mutableStateOf(0f)

    private fun updateTotalSteps(steps: Float) {
        totalSteps.value = steps
    }

    private fun updatePreviousTotalSteps() {
        previousTotalSteps.value = totalSteps.value
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateCurrentCurrentSteps(steps: Float) {
        updateTotalSteps(steps)

        if (LocalTime.now().hour == 23 && LocalTime.now().minute ==
            59 && LocalTime.now().second == 59
        ) {
            // TODO Need to save this instance to database and track
            updatePreviousTotalSteps()
        }

        stepsCountUiState.update {
            it.copy(
                currentSteps = totalSteps.value.minus(previousTotalSteps.value).toInt()
            )
        }
    }
}
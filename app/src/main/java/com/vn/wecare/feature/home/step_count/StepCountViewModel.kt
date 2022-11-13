package com.vn.wecare.feature.home.step_count

import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import androidx.lifecycle.ViewModel
import com.vn.wecare.core.data.HealthConnectManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime

/**
 * Define a model for view presentation
 */
data class StepsCountUiState(
    val currentSteps: Int = 0,
    val caloConsumed: Int = 0,
    val moveMin: Int = 0,
    val isPermissionEnable: Boolean = false, // Check permission to avoid crash
)

// Define a sealed class to present permission state
sealed class StepsPermissionsState {
    object UnInitialized : StepsPermissionsState()
    object Done : StepsPermissionsState()

    data class Error(val exception: Throwable)
}

class StepCountViewModel(
    private val healthConnectManager: HealthConnectManager
) : ViewModel() {

    // Define a list of permissions which steps count feature needs
    val permissions = setOf(
        HealthPermission.createReadPermission(StepsRecord::class),
        HealthPermission.createWritePermission(StepsRecord::class)
    )

    var permissionsGranted = mutableStateOf(false)

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

    fun calculateCurrentCurrentSteps(steps: Float) {
        updateTotalSteps(steps)

        if (LocalTime.now().hour == 23 && LocalTime.now().minute ==
            59 && LocalTime.now().second == 59
        ) {
            // TODO Trigger an alarm manager to save current steps to local db
            updatePreviousTotalSteps()
        }

        stepsCountUiState.update {
            it.copy(
                currentSteps = totalSteps.value.minus(previousTotalSteps.value).toInt()
            )
        }
    }
}
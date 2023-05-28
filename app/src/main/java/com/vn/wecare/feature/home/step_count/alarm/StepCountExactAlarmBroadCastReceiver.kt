package com.vn.wecare.feature.home.step_count.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.vn.wecare.core.checkInternetConnection
import com.vn.wecare.feature.home.step_count.ui.view.StepCountFragment
import com.vn.wecare.feature.home.step_count.usecase.GetCurrentStepsFromSensorUsecase
import com.vn.wecare.feature.home.step_count.usecase.GetStepsPerDayUsecase
import com.vn.wecare.feature.home.step_count.usecase.SaveStepsPerDayToFirestoreUsecase
import com.vn.wecare.feature.home.step_count.usecase.SaveStepsPerDayUsecase
import com.vn.wecare.feature.home.step_count.usecase.UpdatePreviousTotalSensorStepsUsecase
import com.vn.wecare.utils.getEndOfTheDayMilliseconds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StepCountExactAlarmBroadCastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getCurrentStepsFromSensorUsecase: GetCurrentStepsFromSensorUsecase

    @Inject
    lateinit var updatePreviousTotalSensorStepsUsecase: UpdatePreviousTotalSensorStepsUsecase

    @Inject
    lateinit var getStepsPerDayUsecase: GetStepsPerDayUsecase

    @Inject
    lateinit var saveStepsPerDayUsecase: SaveStepsPerDayUsecase

    @Inject
    lateinit var saveStepsPerDayToFirestoreUsecase: SaveStepsPerDayToFirestoreUsecase

    @Inject
    lateinit var stepCountExactAlarms: StepCountExactAlarms

    override fun onReceive(context: Context, intent: Intent?) {
        stepCountExactAlarms.clearExactAlarm()
        CoroutineScope(SupervisorJob()).launch {
            // Get current steps from sensor
            val currentSensorSteps = getCurrentStepsFromSensorUsecase.getCurrentStepsFromSensor()
            Log.d(StepCountFragment.stepCountTag, "Steps from sensor is $currentSensorSteps")
            // Get current day steps and save it to db
            getStepsPerDayUsecase.getCurrentDaySteps(currentSensorSteps).collect {
                Log.d(StepCountFragment.stepCountTag, "Current day step is $it")
                saveStepsPerDayUsecase.saveStepsPerDayToDb(it)
                if (checkInternetConnection(context)) {
                    saveStepsPerDayToFirestoreUsecase.saveStepsPerDayToFirestore(it)
                }
            }
            // Update total steps from sensor
            updatePreviousTotalSensorStepsUsecase.updatePreviousTotalSensorStepCount(
                currentSensorSteps
            )
        }
        stepCountExactAlarms.scheduleExactAlarm(getEndOfTheDayMilliseconds() + 86_400_000)
    }
}
package com.vn.wecare.feature.home.step_count.alarm

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.vn.wecare.core.checkInternetConnection
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.home.step_count.ui.view.StepCountFragment
import com.vn.wecare.feature.home.step_count.usecase.GetCurrentStepsFromSensorUsecase
import com.vn.wecare.feature.home.step_count.usecase.GetStepsPerDayUsecase
import com.vn.wecare.feature.home.step_count.usecase.SaveStepsPerDayToFirestoreUsecase
import com.vn.wecare.feature.home.step_count.usecase.SaveStepsPerDayUsecase
import com.vn.wecare.feature.home.step_count.usecase.UpdatePreviousTotalSensorStepsUsecase
import com.vn.wecare.utils.getEndOfTheDayMilliseconds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
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

    @Inject
    @IoDispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d(StepCountFragment.stepCountTag, "Exact alarm trigger!")

        stepCountExactAlarms.clearExactAlarm()

        // Get current steps from sensor
        val currentSensorSteps = getCurrentStepsFromSensorUsecase.getCurrentStepsFromSensor()
        CoroutineScope(ioDispatcher).launch {
            // Get current day steps and save it to db
            getStepsPerDayUsecase.getCurrentDaySteps(currentSensorSteps).collect {
                saveStepsPerDayUsecase.saveStepsPerDayToDb(it)
//                if (checkInternetConnection(context)) {
//                    saveStepsPerDayToFirestoreUsecase.saveStepsPerDayToFirestore(it)
//                }
            }
        }
        // Update total steps from sensor
        updatePreviousTotalSensorStepsUsecase.updatePreviousTotalSensorStepCount(
            currentSensorSteps
        )
//        stepCountExactAlarms.scheduleExactAlarm(
//            getEndOfTheDayMilliseconds() + AlarmManager.INTERVAL_DAY
//        )
    }
}
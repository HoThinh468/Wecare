package com.vn.wecare.feature.home.step_count.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vn.wecare.core.alarm.ExactAlarms
import com.vn.wecare.core.checkInternetConnection
import com.vn.wecare.feature.home.step_count.usecase.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StepCountInExactAlarmBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getStepsPerDayUsecase: GetStepsPerDayUsecase

    @Inject
    lateinit var getCurrentStepsFromSensorUsecase: GetCurrentStepsFromSensorUsecase

    @Inject
    lateinit var saveStepsPerHourUsecase: SaveStepsPerHourUsecase

    @Inject
    lateinit var getStepsPerHourUsecase: GetStepsPerHourUsecase

    @Inject
    lateinit var stepCountExactAlarms: ExactAlarms

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, p1: Intent?) {
        GlobalScope.launch {
            getStepsPerDayUsecase.getCurrentDaySteps(getCurrentStepsFromSensorUsecase.getCurrentStepsFromSensor())
                .collect { currentDaySteps ->
                    getStepsPerHourUsecase.getCurrentHourSteps(currentDaySteps).collect {
                        saveStepsPerHourUsecase.insertStepsPerHourToDb(it)
                        if (checkInternetConnection(context)) {
                            saveStepsPerHourUsecase.insertStepsPerHourToFirestore(it)
                        }
                    }
                }
        }
        stepCountExactAlarms.scheduleExactAlarm(null)
    }
}
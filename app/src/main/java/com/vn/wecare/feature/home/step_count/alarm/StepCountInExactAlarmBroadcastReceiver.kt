package com.vn.wecare.feature.home.step_count.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.vn.wecare.core.checkInternetConnection
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.home.step_count.ui.view.StepCountFragment
import com.vn.wecare.feature.home.step_count.usecase.GetCurrentStepsFromSensorUsecase
import com.vn.wecare.feature.home.step_count.usecase.GetStepsPerDayUsecase
import com.vn.wecare.feature.home.step_count.usecase.GetStepsPerHourUsecase
import com.vn.wecare.feature.home.step_count.usecase.SaveStepsPerHourUsecase
import com.vn.wecare.utils.getCurrentDayId
import com.vn.wecare.utils.getCurrentHourId
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.combine
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
    @IoDispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    override fun onReceive(context: Context, p1: Intent?) {
        Log.d(StepCountFragment.stepCountTag, "In exact alarm trigger!")

        CoroutineScope(ioDispatcher).launch {
            combine(
                getStepsPerDayUsecase.getCurrentDaySteps(
                    getCurrentStepsFromSensorUsecase.getCurrentStepsFromSensor()
                ),
                getStepsPerHourUsecase.getTotalStepsInDayWithDayId(dayId = getCurrentDayId()),
                getStepsPerHourUsecase.getStepsPerHourWithHourId(getCurrentHourId())
            ) { currentDaySteps, totalStepsInCurrentDay, stepPerHour ->
                if (stepPerHour == null) currentDaySteps - totalStepsInCurrentDay else -1f
            }.collect {
                if (it != -1f) {
                    Log.d(StepCountFragment.stepCountTag, "Steps count in current hour: $it")
                    saveStepsPerHourUsecase.insertStepsPerHourToLocalDb(it)
                    if (checkInternetConnection(context)) {
                        saveStepsPerHourUsecase.insertStepsPerHourToRemoteDb(it)
                    }
                }
            }
        }
    }
}
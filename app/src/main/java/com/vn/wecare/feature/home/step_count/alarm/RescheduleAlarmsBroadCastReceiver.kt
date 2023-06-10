package com.vn.wecare.feature.home.step_count.alarm

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.vn.wecare.feature.home.step_count.ui.view.StepCountFragment
import com.vn.wecare.feature.home.step_count.usecase.UpdatePreviousTotalSensorStepsUsecase
import com.vn.wecare.utils.getEndOfTheDayMilliseconds
import com.vn.wecare.utils.getTheEndOfCurrentHourMilliseconds
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RescheduleAlarmsBroadCastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var stepCountExactAlarms: StepCountExactAlarms

    @Inject
    lateinit var stepCountInExactAlarms: StepCountInExactAlarms

    @Inject
    lateinit var updatePreviousTotalSensorStepsUsecase: UpdatePreviousTotalSensorStepsUsecase

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action != null && action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d(StepCountFragment.stepCountTag, "Phone reboot completed received")
            stepCountExactAlarms.scheduleExactAlarm(getEndOfTheDayMilliseconds())
            stepCountInExactAlarms.scheduleRepeatingInExactAlarm(
                getTheEndOfCurrentHourMilliseconds(), AlarmManager.INTERVAL_HOUR
            )
            updatePreviousTotalSensorStepsUsecase.updatePreviousTotalSensorStepCount(0f)
        }
    }
}
package com.vn.wecare.feature.home.step_count.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vn.wecare.feature.home.step_count.di.STEP_COUNT_SHARED_PREF
import com.vn.wecare.feature.home.step_count.usecase.LATEST_STEPS_COUNT
import com.vn.wecare.feature.home.step_count.usecase.StepCountUsecase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StepCountInExactAlarmBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var stepCountUsecase: StepCountUsecase

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, p1: Intent?) {
        val sharePref = context?.getSharedPreferences(STEP_COUNT_SHARED_PREF, Context.MODE_PRIVATE)
        val currentStepsFromSensor = sharePref?.getFloat(LATEST_STEPS_COUNT, 0f)
        GlobalScope.launch {
            stepCountUsecase.calculateCurrentDaySteps(currentStepsFromSensor ?: 0f)
                .collect { currentDaySteps ->
                    stepCountUsecase.insertStepsPerHourToDb(
                        stepCountUsecase.getCurrentHourSteps(
                            currentDaySteps
                        )
                    )
                }
        }
    }
}
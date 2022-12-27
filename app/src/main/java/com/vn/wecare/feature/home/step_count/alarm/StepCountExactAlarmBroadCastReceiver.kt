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
class StepCountExactAlarmBroadCastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var stepCountUsecase: StepCountUsecase

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent?) {
        val sharedPref = context.getSharedPreferences(STEP_COUNT_SHARED_PREF, Context.MODE_PRIVATE)
        val currentStepsFromSensor = sharedPref.getFloat(LATEST_STEPS_COUNT, 0f)

        stepCountUsecase.updatePreviousTotalSensorStepCount(currentStepsFromSensor)

        GlobalScope.launch {
            stepCountUsecase.calculateCurrentDaySteps(currentStepsFromSensor).collect { currentDaySteps ->
                // TODO: Call a function to insert steps per day to db
            }
        }
    }
}
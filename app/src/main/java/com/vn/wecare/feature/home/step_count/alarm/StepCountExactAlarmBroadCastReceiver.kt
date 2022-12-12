package com.vn.wecare.feature.home.step_count.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.vn.wecare.feature.home.step_count.StepCountUsecase
import com.vn.wecare.feature.home.step_count.ui.compose.MotionSensor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StepCountExactAlarmBroadCastReceiver : BroadcastReceiver() {

    @Inject lateinit var motionSensor: MotionSensor
    @Inject lateinit var stepCountUsecase: StepCountUsecase

    private fun showToast(context: Context) {
        Toast.makeText(context, motionSensor.currentSteps.value.toString(), Toast.LENGTH_LONG)
            .show()
    }

    override fun onReceive(context: Context, intent: Intent?) {
//        showToast(context)
    }
}
package com.vn.wecare.feature.home.step_count.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.utils.getNextEndHourInMillis
import javax.inject.Inject

class StepCountExactAlarmBroadCastReceiver(): BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent?) {
        // TODO Call a function to save current hour to db


        // Call a function to schedule a new alarm next hour
        val alarm = StepCountExactAlarms(context)
        alarm.scheduleExactAlarm(getNextEndHourInMillis())
    }

}
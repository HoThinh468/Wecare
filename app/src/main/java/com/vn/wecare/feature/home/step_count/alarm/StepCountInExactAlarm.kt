package com.vn.wecare.feature.home.step_count.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.vn.wecare.core.alarm.IN_EXACT_ALARM_REQUEST_CODE
import com.vn.wecare.core.alarm.InExactAlarms
import javax.inject.Inject

const val ALARM_REQUEST_CODE_EXTRA = "alarm_request_code_extra"

const val IS_STEP_COUNT_INEXACT_ALARM_SET = "is_set"

class StepCountInExactAlarms @Inject constructor(private val context: Context) : InExactAlarms {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun scheduleInExactAlarm(triggerAtMillis: Long, intervalMillis: Long) {
        setRepeatAlarm(triggerAtMillis, intervalMillis)
    }

    override fun clearInExactAlarm() {
        cancelInExactAlarm()
    }

    private fun cancelInExactAlarm() {
        val pendingIntent = createInExactAlarmIntent()
        alarmManager.cancel(pendingIntent)
    }

    private fun setRepeatAlarm(triggerAtMillis: Long, intervalMillis: Long) {
        val sharedPref = context.getSharedPreferences(STEP_COUNT_ALARM, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean(IS_STEP_COUNT_INEXACT_ALARM_SET, false)
        }
        val pendingIntent = createInExactAlarmIntent()
        alarmManager.setRepeating(
            AlarmManager.RTC, triggerAtMillis, intervalMillis, pendingIntent
        )
    }

    private fun createInExactAlarmIntent(): PendingIntent? {
        val intent = Intent(context, StepCountInExactAlarmBroadcastReceiver::class.java).apply {
            putExtra(ALARM_REQUEST_CODE_EXTRA, IN_EXACT_ALARM_REQUEST_CODE)
        }
        // Flag indicating that the created PendingIntent should be immutable.
        // This means that the additional intent argument passed to the send methods to fill
        // in unpopulated properties of this intent will be ignored.
        return PendingIntent.getBroadcast(
            context, IN_EXACT_ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE
        )
    }
}
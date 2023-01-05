package com.vn.wecare.feature.home.step_count.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.vn.wecare.core.alarm.EXACT_ALARM_INTENT_AT_THE_END_OF_DAY_CODE
import com.vn.wecare.core.alarm.EXACT_ALARM_INTENT_REQUEST_CODE
import com.vn.wecare.core.alarm.ExactAlarms
import com.vn.wecare.utils.getEndOfTheDayMilliseconds
import javax.inject.Inject

const val STEP_COUNT_ALARM = "step_count_alarm"

class StepCountExactAlarms @Inject constructor(private val context: Context) : ExactAlarms {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun scheduleExactAlarm(triggerAtMillis: Long) {
        setExactAlarm(triggerAtMillis)
    }

    override fun scheduleExactAlarm(triggerAtMillis: Long?) {
        triggerSaveDataAtTheEndOfTheDay()
    }

    override fun clearExactAlarm() {
        val pendingIntent = createExactAlarmIntent(null)
        alarmManager.cancel(pendingIntent)
    }

    override fun canScheduleExactAlarm(): Boolean {
        // Check if app has a permission to schedule an exact alarm
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else true
    }

    private fun setExactAlarm(triggerAtMillis: Long) {
        val pendingIntent = createExactAlarmIntent(null)
        // Alarm repeat every hour
        alarmManager.setExact(
            AlarmManager.RTC, triggerAtMillis, pendingIntent
        )
    }

    private fun triggerSaveDataAtTheEndOfTheDay() {
        val pendingIntent = createExactAlarmIntent(EXACT_ALARM_INTENT_AT_THE_END_OF_DAY_CODE)
        alarmManager.setExact(AlarmManager.RTC, getEndOfTheDayMilliseconds(), pendingIntent)
    }

    /**
     * Create pending intent for an exact alarm
     */
    private fun createExactAlarmIntent(
        requestCode: Int?
    ): PendingIntent {
        val intent = Intent(context, StepCountExactAlarmBroadCastReceiver::class.java)
        // Flag indicating that the created PendingIntent should be immutable.
        // This means that the additional intent argument passed to the send methods to fill
        // in unpopulated properties of this intent will be ignored.
        return PendingIntent.getBroadcast(
            context,
            requestCode ?: EXACT_ALARM_INTENT_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}
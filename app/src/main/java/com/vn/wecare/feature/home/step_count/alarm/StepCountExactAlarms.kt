package com.vn.wecare.feature.home.step_count.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.vn.wecare.core.alarm.EXACT_ALARM_INTENT_REQUEST_CODE
import com.vn.wecare.core.alarm.ExactAlarms
import java.time.LocalDateTime
import javax.inject.Inject

class StepCountExactAlarms @Inject constructor(private val context: Context) : ExactAlarms {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun scheduleExactAlarm(triggerAtMillis: Long) {
        setExactAlarm(triggerAtMillis)
    }

    override fun clearExactAlarm() {
        val pendingIntent = createExactAlarmIntent(PendingIntent.FLAG_NO_CREATE)
        alarmManager.cancel(pendingIntent)
    }

    override fun canScheduleExactAlarm(): Boolean {
        // Check if app has a permission to schedule an exact alarm
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else true
    }

    override fun isScheduleSet(): Boolean {
        TODO("Not yet implemented")

    }

    private fun setExactAlarm(triggerAtMillis: Long) {
        val pendingIntent = createExactAlarmIntent(null)

        // Alarm repeat every hour
        alarmManager.setExact(
            AlarmManager.RTC, triggerAtMillis, pendingIntent
        )
    }

    /**
     * Create pending intent for an exact alarm
     */
    private fun createExactAlarmIntent(flag: Int?): PendingIntent {
        val intent = Intent(context, StepCountExactAlarmBroadCastReceiver::class.java)
        // Flag indicating that the created PendingIntent should be immutable.
        // This means that the additional intent argument passed to the send methods to fill
        // in unpopulated properties of this intent will be ignored.
        return PendingIntent.getBroadcast(
            context, EXACT_ALARM_INTENT_REQUEST_CODE, intent, flag ?: PendingIntent.FLAG_IMMUTABLE
        )
    }
}
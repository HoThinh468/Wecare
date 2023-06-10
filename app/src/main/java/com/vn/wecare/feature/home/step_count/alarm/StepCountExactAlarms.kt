package com.vn.wecare.feature.home.step_count.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.content.edit
import com.vn.wecare.core.WecareSharePreferences
import com.vn.wecare.core.alarm.EXACT_ALARM_INTENT_AT_THE_END_OF_DAY_CODE
import com.vn.wecare.core.alarm.ExactAlarms
import com.vn.wecare.feature.home.step_count.ui.view.StepCountFragment
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val STEP_COUNT_ALARM = "step_count_alarm"
const val IS_STEP_COUNT_EXACT_ALARM_SET = "is_step_count_exact_alarm_set"

class StepCountExactAlarms @Inject constructor(
    @ApplicationContext private val context: Context, private val sharedPref: WecareSharePreferences
) : ExactAlarms {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun scheduleExactAlarm(triggerAtMillis: Long) {
        val pendingIntent = getExactAlarmPendingIntent()
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
        sharedPref.getDefaultSharedPref(STEP_COUNT_ALARM).edit {
            putBoolean(IS_STEP_COUNT_EXACT_ALARM_SET, true)
            apply()
        }
    }

    override fun clearExactAlarm() {
        val pendingIntent = getExactAlarmPendingIntent()
        alarmManager.cancel(pendingIntent)
        sharedPref.getDefaultSharedPref(STEP_COUNT_ALARM).edit {
            putBoolean(IS_STEP_COUNT_EXACT_ALARM_SET, false)
            apply()
        }
        Log.d(
            StepCountFragment.stepCountTag, "Exact alarm cleared"
        )
    }

    override fun canScheduleExactAlarm(): Boolean {
        // Check if app has a permission to schedule an exact alarm
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else true
    }

    override fun isExactAlarmSet(): Boolean {
        return sharedPref.getDefaultSharedPref(STEP_COUNT_ALARM).getBoolean(
            IS_STEP_COUNT_EXACT_ALARM_SET, false
        )
    }

    private fun getExactAlarmPendingIntent(): PendingIntent {
        val intent = Intent(context, StepCountExactAlarmBroadCastReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            EXACT_ALARM_INTENT_AT_THE_END_OF_DAY_CODE,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
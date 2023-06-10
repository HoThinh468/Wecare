package com.vn.wecare.feature.home.step_count.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.edit
import com.vn.wecare.core.WecareSharePreferences
import com.vn.wecare.core.alarm.IN_EXACT_ALARM_REQUEST_CODE
import com.vn.wecare.core.alarm.InExactAlarms
import com.vn.wecare.feature.home.step_count.ui.view.StepCountFragment
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val IS_STEP_COUNT_INEXACT_ALARM_SET = "is_step_count_in_exact_alarm_set"

class StepCountInExactAlarms @Inject constructor(
    @ApplicationContext private val context: Context, sharedPref: WecareSharePreferences
) : InExactAlarms {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private val stepCountSharedPref = sharedPref.getDefaultSharedPref(STEP_COUNT_ALARM)

    override fun scheduleRepeatingInExactAlarm(triggerAtMillis: Long, intervalMillis: Long) {
        val pendingIntent = getInExactPendingIntent()
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, pendingIntent
        )
        stepCountSharedPref.edit {
            putBoolean(IS_STEP_COUNT_INEXACT_ALARM_SET, true)
            apply()
        }
    }

    override fun clearInExactAlarm() {
        alarmManager.cancel(getInExactPendingIntent())
        stepCountSharedPref.edit {
            putBoolean(IS_STEP_COUNT_INEXACT_ALARM_SET, false)
            apply()
        }
        Log.d(
            StepCountFragment.stepCountTag, "Inexact alarm cleared"
        )
    }

    override fun isInExactAlarmSet(): Boolean = stepCountSharedPref.getBoolean(
        IS_STEP_COUNT_INEXACT_ALARM_SET, false
    )

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun getInExactPendingIntent(): PendingIntent {
        val intent = Intent(context, StepCountInExactAlarmBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            IN_EXACT_ALARM_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
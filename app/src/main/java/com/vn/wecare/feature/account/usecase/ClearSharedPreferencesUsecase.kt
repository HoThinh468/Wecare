package com.vn.wecare.feature.account.usecase

import android.content.Context
import android.util.Log
import com.vn.wecare.core.STEP_COUNT_SHARED_PREF
import com.vn.wecare.feature.home.step_count.alarm.IS_STEP_COUNT_INEXACT_ALARM_SET
import com.vn.wecare.feature.home.step_count.alarm.STEP_COUNT_ALARM
import com.vn.wecare.feature.home.step_count.usecase.CURRENT_STEP_FROM_SENSOR
import com.vn.wecare.feature.home.step_count.usecase.PREVIOUS_TOTAL_SENSOR_STEPS
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ClearSharedPreferencesUsecase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun clearAllSharedPref() {
        val stepCountSharePref =
            context.getSharedPreferences(STEP_COUNT_SHARED_PREF, Context.MODE_PRIVATE)
        with(stepCountSharePref.edit()) {
            Log.d("Clear latest step count share pref", "")
            remove(CURRENT_STEP_FROM_SENSOR)
            remove(PREVIOUS_TOTAL_SENSOR_STEPS)
            apply()
        }

        val alarmSharePref = context.getSharedPreferences(STEP_COUNT_ALARM, Context.MODE_PRIVATE)
        with(alarmSharePref.edit()) {
            Log.d("Clear step count alarm", "")
            remove(IS_STEP_COUNT_INEXACT_ALARM_SET)
            apply()
        }
    }
}
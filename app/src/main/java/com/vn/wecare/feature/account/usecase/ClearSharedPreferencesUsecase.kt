package com.vn.wecare.feature.account.usecase

import android.content.Context
import android.util.Log
import com.vn.wecare.feature.home.step_count.alarm.IS_STEP_COUNT_INEXACT_ALARM_SET
import com.vn.wecare.feature.home.step_count.alarm.STEP_COUNT_ALARM
import com.vn.wecare.feature.home.step_count.di.STEP_COUNT_SHARED_PREF
import com.vn.wecare.feature.home.step_count.usecase.LATEST_STEPS_COUNT
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
            remove(LATEST_STEPS_COUNT)
            remove(PREVIOUS_TOTAL_SENSOR_STEPS)
            apply()
//            clear()
//            commit()
        }

        val alarmSharePref = context.getSharedPreferences(STEP_COUNT_ALARM, Context.MODE_PRIVATE)
        with(alarmSharePref.edit()) {
            Log.d("Clear step count alarm", "")
//            clear()
//            commit()
            remove(IS_STEP_COUNT_INEXACT_ALARM_SET)
            apply()
        }
    }
}
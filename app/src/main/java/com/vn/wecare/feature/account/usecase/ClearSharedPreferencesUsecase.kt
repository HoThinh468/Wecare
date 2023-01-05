package com.vn.wecare.feature.account.usecase

import android.content.Context
import com.vn.wecare.feature.home.step_count.alarm.IS_STEP_COUNT_INEXACT_ALARM_SET
import com.vn.wecare.feature.home.step_count.alarm.STEP_COUNT_ALARM
import com.vn.wecare.feature.home.step_count.di.STEP_COUNT_SHARED_PREF
import com.vn.wecare.feature.home.step_count.usecase.LATEST_STEPS_COUNT
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ClearSharedPreferencesUsecase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun clearAllSharedPref() {
        val stepCountSharePref = context.getSharedPreferences(STEP_COUNT_SHARED_PREF, Context.MODE_PRIVATE)
        with(stepCountSharePref.edit()) {
            remove(LATEST_STEPS_COUNT)
            apply()
        }

        val alarmSharePref = context.getSharedPreferences(STEP_COUNT_ALARM, Context.MODE_PRIVATE)
        with(alarmSharePref.edit()) {
            remove(IS_STEP_COUNT_INEXACT_ALARM_SET)
            apply()
        }
    }
}
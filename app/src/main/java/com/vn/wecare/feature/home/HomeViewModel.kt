package com.vn.wecare.feature.home

import androidx.lifecycle.ViewModel
import com.vn.wecare.core.alarm.InExactAlarms
import com.vn.wecare.feature.home.step_count.alarm.StepCountExactAlarms
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val stepCountInExactAlarms: InExactAlarms,
    private val stepCountExactAlarms: StepCountExactAlarms
) : ViewModel() {

    fun cancelInExactAlarm() {
//        stepCountInExactAlarms.clearInExactAlarm()
//        stepCountExactAlarms.clearExactAlarm()
    }
}
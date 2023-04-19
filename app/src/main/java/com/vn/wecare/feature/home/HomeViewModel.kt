package com.vn.wecare.feature.home

import androidx.lifecycle.ViewModel
import com.vn.wecare.core.alarm.InExactAlarms
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val stepCountInExactAlarms: InExactAlarms,
) : ViewModel() {

    fun cancelInExactAlarm() {
        stepCountInExactAlarms.clearInExactAlarm()
    }
}
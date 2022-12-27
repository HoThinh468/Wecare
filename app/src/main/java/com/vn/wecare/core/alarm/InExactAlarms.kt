package com.vn.wecare.core.alarm

interface InExactAlarms {

    fun scheduleInExactAlarm(triggerAtMillis: Long, intervalMillis: Long)

    fun clearInExactAlarm()
}
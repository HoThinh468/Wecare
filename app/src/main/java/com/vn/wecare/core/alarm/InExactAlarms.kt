package com.vn.wecare.core.alarm

interface InExactAlarms {

    fun scheduleRepeatingInExactAlarm(triggerAtMillis: Long, intervalMillis: Long)

    fun clearInExactAlarm()

    fun isInExactAlarmSet(): Boolean
}
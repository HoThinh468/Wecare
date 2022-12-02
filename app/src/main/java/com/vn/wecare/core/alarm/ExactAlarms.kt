package com.vn.wecare.core.alarm

interface ExactAlarms {

    fun scheduleExactAlarm(triggerAtMillis: Long, intervalMillis: Long)

    fun clearExactAlarm()

    fun canScheduleExactAlarm(): Boolean
}
package com.vn.wecare.core.alarm

interface ExactAlarms {

    fun scheduleExactAlarm(triggerAtMillis: Long)

    fun scheduleExactAlarm(triggerAtMillis: Long?)

    fun clearExactAlarm()

    fun canScheduleExactAlarm(): Boolean
}
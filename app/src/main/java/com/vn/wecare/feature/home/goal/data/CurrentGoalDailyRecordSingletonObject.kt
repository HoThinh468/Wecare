package com.vn.wecare.feature.home.goal.data

import com.vn.wecare.feature.home.goal.data.model.GoalDailyRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object CurrentGoalDailyRecordSingletonObject {
    private val instance = MutableStateFlow(GoalDailyRecord())

    fun getInstance() = instance.value

    fun getInstanceFlow() = instance.asStateFlow()

    fun updateInstance(newDay: GoalDailyRecord) {
        instance.value = newDay
    }
}
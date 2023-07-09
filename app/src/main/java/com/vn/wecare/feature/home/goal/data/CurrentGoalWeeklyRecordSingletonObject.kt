package com.vn.wecare.feature.home.goal.data

import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object CurrentGoalWeeklyRecordSingletonObject {
    private val instance = MutableStateFlow(GoalWeeklyRecord())

    fun getInstance() = instance.value

    fun getInstanceFlow() = instance.asStateFlow()

    fun updateInstance(newWeek: GoalWeeklyRecord) {
        instance.value = newWeek
    }
}
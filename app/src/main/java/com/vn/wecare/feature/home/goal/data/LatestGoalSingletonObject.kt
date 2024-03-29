package com.vn.wecare.feature.home.goal.data

import com.vn.wecare.feature.home.goal.data.model.Goal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object LatestGoalSingletonObject {
    private val instance = MutableStateFlow(Goal())

    fun getInStance() = instance.value

    fun getInStanceFlow() = instance.asStateFlow()

    fun updateInStance(newGoal: Goal) {
        instance.value = newGoal
    }
}
package com.vn.wecare.feature.goal

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object GoalSingletonObject {
    private val instance = MutableStateFlow(Goal())

    fun getInStance() = instance.value

    fun getInStanceFlow() = instance.asStateFlow()

    fun updateInStance(newGoal: Goal) {
        instance.value = newGoal
    }
}
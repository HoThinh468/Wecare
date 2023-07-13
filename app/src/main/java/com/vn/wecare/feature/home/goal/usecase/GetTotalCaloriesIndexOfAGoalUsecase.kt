package com.vn.wecare.feature.home.goal.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.goal.data.GoalsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTotalCaloriesIndexOfAGoalUsecase @Inject constructor(
    private val repo: GoalsRepository
) {

    fun getTotalCaloriesInIndex(goalId: String): Flow<Int> = flow {
        var totalIndex = 0
        repo.getAllWeeklyRecordsOfAGoal(goalId).collect { res ->
            if (res is Response.Success && res.data.isNotEmpty()) {
                for (i in res.data) {
                    totalIndex += i.caloriesIn
                }
            }
        }
        emit(totalIndex)
    }

    fun getTotalCaloriesOutIndex(goalId: String): Flow<Int> = flow {
        var totalIndex = 0
        repo.getAllWeeklyRecordsOfAGoal(goalId).collect { res ->
            if (res is Response.Success && res.data.isNotEmpty()) {
                for (i in res.data) {
                    totalIndex += i.caloriesOut
                }
            }
        }
        emit(totalIndex)
    }
}
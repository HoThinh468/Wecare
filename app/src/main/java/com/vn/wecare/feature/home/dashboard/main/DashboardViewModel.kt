package com.vn.wecare.feature.home.dashboard.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.feature.food.usecase.GetTotalInputCaloriesUsecase
import com.vn.wecare.utils.getProgressInFloatWithIntInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class DashboardCaloriesUiState(
    val remainedCalories: Int = 0,
    val caloriesIn: Int = 0,
    val caloriesInProgress: Float = 0f,
    val caloriesOut: Int = 0,
    val caloriesOutProgress: Float = 0f,
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getTotalInputCaloriesUsecase: GetTotalInputCaloriesUsecase
) : ViewModel() {

    private val currentDate = LocalDate.now()

    private val _dashboardCaloriesUiState = MutableStateFlow(DashboardCaloriesUiState())
    val dashboardCaloriesUiState = _dashboardCaloriesUiState.asStateFlow()

    fun initDashboardUiState() {
        initCaloriesOverviewUi()
    }

    private fun initCaloriesOverviewUi() {
        val caloriesObj = WecareCaloriesObject.getInstance()
        updateCaloriesIn(caloriesObj.caloriesInEachDay)
    }

    private fun updateCaloriesIn(caloriesInGoal: Int) = viewModelScope.launch {
        getTotalInputCaloriesUsecase.getTotalInputCaloriesOfEachDay(
            currentDate.dayOfMonth, currentDate.monthValue - 1, currentDate.year
        ).collect { res ->
            if (res is Response.Success) {
                _dashboardCaloriesUiState.update {
                    it.copy(
                        caloriesIn = res.data, caloriesInProgress = getProgressInFloatWithIntInput(
                            res.data, caloriesInGoal
                        )
                    )
                }
            } else {
                _dashboardCaloriesUiState.update {
                    it.copy(caloriesIn = 0, caloriesInProgress = 0f)
                }
            }
        }
    }
}
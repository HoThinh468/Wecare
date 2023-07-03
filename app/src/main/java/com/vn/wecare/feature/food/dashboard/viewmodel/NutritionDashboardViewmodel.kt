package com.vn.wecare.feature.food.dashboard.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.feature.food.usecase.CalculateNutrientsIndexUsecase
import com.vn.wecare.feature.food.usecase.GetTotalInputCaloriesUsecase
import com.vn.wecare.feature.food.usecase.GetTotalNutrientsIndexUsecase
import com.vn.wecare.utils.getDayOfWeekPrefix
import com.vn.wecare.utils.getMonthPrefix
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class NutritionDashboardUiState(
    val updateState: Response<Boolean>? = null,
    val dateTime: String = "",
    val targetCaloriesAmount: Int = 0,
    val targetProteinIndex: Int = 0,
    val targetFatIndex: Int = 0,
    val targetCarbsIndex: Int = 0,
    val breakfastCurrentCalories: Int = 0,
    val breakfastTargetCalories: Int = 0,
    val lunchCurrentCalories: Int = 0,
    val lunchTargetCalories: Int = 0,
    val snackCurrentCalories: Int = 0,
    val snackTargetCalories: Int = 0,
    val dinnerCurrentCalories: Int = 0,
    val dinnerTargetCalories: Int = 0
)

@HiltViewModel
class NutritionDashboardViewmodel @Inject constructor(
    private val calculateNutrientsIndexUsecase: CalculateNutrientsIndexUsecase,
    private val getTotalInputCaloriesUsecase: GetTotalInputCaloriesUsecase,
    private val getTotalNutrientsIndexUsecase: GetTotalNutrientsIndexUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NutritionDashboardUiState())
    val uiState = _uiState.asStateFlow()

    private val caloriesObject = WecareCaloriesObject.getInstanceFlow()

    private val calendar = Calendar.getInstance()
    private val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    private val currentMonth = calendar.get(Calendar.MONTH)
    private val currentYear = calendar.get(Calendar.YEAR)

    var totalCalories by mutableStateOf(0)
    var totalProtein by mutableStateOf(0)
    var totalFat by mutableStateOf(0)
    var totalCarbs by mutableStateOf(0)

    fun initUiState() {
        updateDailyNutrientsUiState()
        updateDateTime()
        updateTotalInputCaloriesOfToday()
        updateBreakfastCurrentCalories()
        updateLunchCurrentCalories()
        updateSnackCurrentCalories()
        updateDinnerCurrentCalories()
        updateTotalProteinToday()
        updateTotalFatToday()
        updateTotalCarbsToday()
    }

    private fun updateDailyNutrientsUiState() = viewModelScope.launch {
        caloriesObject.collect { obj ->
            _uiState.update {
                it.copy(
                    targetCaloriesAmount = obj.caloriesInEachDay,
                    breakfastTargetCalories = obj.caloriesOfBreakfast,
                    lunchTargetCalories = obj.caloriesOfLunch,
                    snackTargetCalories = obj.caloriesOfSnack,
                    dinnerTargetCalories = obj.caloriesOfDinner,
                    targetProteinIndex = calculateNutrientsIndexUsecase.getProteinIndexInGram(obj.caloriesInEachDay),
                    targetCarbsIndex = calculateNutrientsIndexUsecase.getCarbIndexInGram(obj.caloriesOfDinner),
                    targetFatIndex = calculateNutrientsIndexUsecase.getFatIndexInGram(obj.caloriesInEachDay)
                )
            }
        }
    }

    private fun updateDateTime() {
        val dayInWeek = getDayOfWeekPrefix(calendar.get(Calendar.DAY_OF_WEEK))
        val monthPrefix = getMonthPrefix(calendar.get(Calendar.MONTH) + 1)
        val dateTime = "${dayInWeek}, ${calendar.get(Calendar.DAY_OF_MONTH)} $monthPrefix"
        _uiState.update {
            it.copy(dateTime = dateTime)
        }
    }

    private fun updateTotalInputCaloriesOfToday() = viewModelScope.launch {
        getTotalInputCaloriesUsecase.getTotalInputCaloriesOfEachDay(
            currentDay, currentMonth, currentYear
        ).collect { res ->
            totalCalories = if (res is Response.Success) {
                res.data
            } else 0
        }
    }

    private fun updateBreakfastCurrentCalories() = viewModelScope.launch {
        getTotalInputCaloriesUsecase.getTotalInputCaloriesOfEachDayForBreakfast(
            currentDay, currentMonth, currentYear
        ).collect { res ->
            if (res is Response.Success) {
                _uiState.update { it.copy(breakfastCurrentCalories = res.data) }
            } else _uiState.update { it.copy(breakfastCurrentCalories = 0) }
        }
    }

    private fun updateLunchCurrentCalories() = viewModelScope.launch {
        getTotalInputCaloriesUsecase.getTotalInputCaloriesOfEachDayForLunch(
            currentDay, currentMonth, currentYear
        ).collect { res ->
            if (res is Response.Success) {
                _uiState.update { it.copy(lunchCurrentCalories = res.data) }
            } else _uiState.update { it.copy(lunchCurrentCalories = 0) }
        }
    }

    private fun updateSnackCurrentCalories() = viewModelScope.launch {
        getTotalInputCaloriesUsecase.getTotalInputCaloriesOfEachDayForSnack(
            currentDay, currentMonth, currentYear
        ).collect { res ->
            if (res is Response.Success) {
                _uiState.update { it.copy(snackCurrentCalories = res.data) }
            } else _uiState.update { it.copy(snackCurrentCalories = 0) }
        }
    }

    private fun updateDinnerCurrentCalories() = viewModelScope.launch {
        getTotalInputCaloriesUsecase.getTotalInputCaloriesOfEachDayForDinner(
            currentDay, currentMonth, currentYear
        ).collect { res ->
            if (res is Response.Success) {
                _uiState.update { it.copy(dinnerCurrentCalories = res.data) }
            } else _uiState.update { it.copy(dinnerCurrentCalories = 0) }
        }
    }

    private fun updateTotalProteinToday() = viewModelScope.launch {
        getTotalNutrientsIndexUsecase.getTotalProteinOfEachDay(
            currentDay, currentMonth, currentYear,
        ).collect { res ->
            totalProtein = if (res is Response.Success) {
                res.data
            } else 0
        }
    }

    private fun updateTotalFatToday() = viewModelScope.launch {
        getTotalNutrientsIndexUsecase.getTotalFatOfEachDay(
            currentDay, currentMonth, currentYear,
        ).collect { res ->
            totalFat = if (res is Response.Success) {
                res.data
            } else 0
        }
    }

    private fun updateTotalCarbsToday() = viewModelScope.launch {
        getTotalNutrientsIndexUsecase.getTotalCarbsOfEachDay(
            currentDay, currentMonth, currentYear,
        ).collect { res ->
            totalCarbs = if (res is Response.Success) {
                res.data
            } else 0
        }
    }

    fun resetNutrientIndex() {
        totalCalories = 0
        totalProtein = 0
        totalFat = 0
        totalCarbs = 0
        _uiState.update {
            NutritionDashboardUiState()
        }
    }
}
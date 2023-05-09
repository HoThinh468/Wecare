package com.vn.wecare.feature.food.nutrition.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.feature.food.nutrition.WecareCaloriesObject
import com.vn.wecare.feature.food.nutrition.usecase.CalculateNutrientsIndexUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DailyNutritionUiState(
    val currentCaloriesAmount: Int = 0,
    val targetCaloriesAmount: Int = 0,
    val currentProteinIndex: Int = 0,
    val targetProteinIndex: Int = 0,
    val currentFatIndex: Int = 0,
    val targetFatIndex: Int = 0,
    val currentCarbsIndex: Int = 0,
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
class DailyNutritionViewmodel @Inject constructor(
    private val calculateNutrientsIndexUsecase: CalculateNutrientsIndexUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyNutritionUiState())
    val uiState = _uiState.asStateFlow()

    private val caloriesObject = WecareCaloriesObject.getInstanceFlow()

    init {
        updateDailyNutrientsUiState()
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
}
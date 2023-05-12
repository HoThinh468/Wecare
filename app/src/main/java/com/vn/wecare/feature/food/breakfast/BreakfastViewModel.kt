package com.vn.wecare.feature.food.breakfast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.addmeal.data.model.MealRecordModel
import com.vn.wecare.feature.food.addmeal.data.model.MealTypeKey
import com.vn.wecare.feature.food.addmeal.usecase.GetMealsWithDayIdUsecase
import com.vn.wecare.feature.food.nutrition.WecareCaloriesObject
import com.vn.wecare.feature.food.nutrition.usecase.CalculateNutrientsIndexUsecase
import com.vn.wecare.utils.getMonthPrefix
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class BreakfastUiState(
    val dateTime: String = "",
    val calories: Int = 0,
    val targetCalories: Int = 0,
    val protein: Int = 0,
    val targetProtein: Int = 0,
    val fat: Int = 0,
    val targetFat: Int = 0,
    val carbs: Int = 0,
    val targetCarbs: Int = 0,
    val getMealsResponse: Response<Boolean>? = null,
    val mealRecords: List<MealRecordModel> = emptyList()
)

@HiltViewModel
class BreakfastViewModel @Inject constructor(
    private val getMealsWithDayIdUsecase: GetMealsWithDayIdUsecase,
    private val calculateNutrientsIndexUsecase: CalculateNutrientsIndexUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BreakfastUiState())
    val uiState = _uiState.asStateFlow()

    fun initUiState() {
        val calendar = Calendar.getInstance()
        updateDateTime(
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.YEAR)
        )
        getBreakfastMealList(
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.YEAR)
        )
        updateTargetNutrientsInformation()
    }

    fun onDateChangeListener(dayOfMonth: Int, month: Int, year: Int) {
        updateDateTime(dayOfMonth, month, year)
        getBreakfastMealList(dayOfMonth, month, year)
    }

    private fun updateTargetNutrientsInformation() = viewModelScope.launch {
        val caloriesObj = WecareCaloriesObject.getInstanceFlow()
        caloriesObj.collect { obj ->
            _uiState.update {
                it.copy(
                    targetCalories = obj.caloriesOfBreakfast,
                    targetProtein = calculateNutrientsIndexUsecase.getProteinIndexInGram(obj.caloriesOfBreakfast),
                    targetFat = calculateNutrientsIndexUsecase.getFatIndexInGram(obj.caloriesOfBreakfast),
                    targetCarbs = calculateNutrientsIndexUsecase.getCarbIndexInGram(obj.caloriesOfBreakfast)
                )
            }
        }
    }

    private fun updateDateTime(dayOfMonth: Int, month: Int, year: Int) {
        _uiState.update {
            it.copy(
                dateTime = "${dayOfMonth}, ${getMonthPrefix(month + 1)} $year"
            )
        }
    }

    private fun getBreakfastMealList(dayOfMonth: Int, month: Int, year: Int) =
        viewModelScope.launch {
            _uiState.update { it.copy(getMealsResponse = Response.Loading) }
            getMealsWithDayIdUsecase.getMealOfEachTypeInDayWithDayId(
                dayOfMonth, month, year, MealTypeKey.BREAKFAST
            ).collect { res ->
                if (res is Response.Success) {
                    _uiState.update { it.copy(mealRecords = res.data ?: emptyList()) }
                    updateNutritionIndex(res.data ?: emptyList())
                    _uiState.update { it.copy(getMealsResponse = Response.Success(true)) }
                } else {
                    _uiState.update { it.copy(getMealsResponse = Response.Error(null)) }
                }
            }
        }

    private fun updateNutritionIndex(recordList: List<MealRecordModel>) {
        if (recordList.isNotEmpty()) {
            var totalCalories = 0
            for (item in recordList) {
                totalCalories += item.calories
            }
            _uiState.update {
                it.copy(
                    calories = totalCalories,
                    protein = calculateNutrientsIndexUsecase.getProteinIndexInGram(totalCalories),
                    fat = calculateNutrientsIndexUsecase.getFatIndexInGram(totalCalories),
                    carbs = calculateNutrientsIndexUsecase.getCarbIndexInGram(totalCalories),
                )
            }
        } else {
            resetNutrientIndex()
        }
    }

    private fun resetNutrientIndex() {
        _uiState.update {
            it.copy(
                calories = 0, protein = 0, fat = 0, carbs = 0
            )
        }
    }
}
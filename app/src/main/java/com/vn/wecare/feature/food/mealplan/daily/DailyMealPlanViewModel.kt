package com.vn.wecare.feature.food.mealplan.daily

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.feature.food.data.model.mealplan.MealPlanResult
import com.vn.wecare.feature.food.data.repository.MealPlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

data class DailyMealPlanUiState(
    val getMealPlanResponse: Response<Boolean>? = null,
    val dailyMealPlan: MealPlanResult = MealPlanResult(),
    val totalCalories: Int = 0,
    val totalProtein: Int = 0,
    val totalFat: Int = 0,
    val totalCarbs: Int = 0,
)

@HiltViewModel
class DailyMealPlanViewModel @Inject constructor(
    private val repo: MealPlanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyMealPlanUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getDailyMealPlanResult()
    }

    fun getDailyMealPlanResult() = viewModelScope.launch {
        _uiState.update { it.copy(getMealPlanResponse = Response.Loading) }
        repo.getMealPlanWithCalories(WecareCaloriesObject.getInstance().caloriesInEachDay)
            .collect { res ->
                Log.d(DailyMealPlanFragment.dailyMealPlanTag, "Meal plan res: $res")
                if (res is Response.Success) {
                    _uiState.update {
                        it.copy(
                            dailyMealPlan = res.data,
                            getMealPlanResponse = Response.Success(true),
                            totalCalories = res.data.nutrients.calories.toInt(),
                            totalProtein = res.data.nutrients.protein.toInt(),
                            totalFat = res.data.nutrients.fat.toInt(),
                            totalCarbs = res.data.nutrients.carbs.toInt()
                        )
                    }
                } else {
                    _uiState.update { it.copy(getMealPlanResponse = Response.Error(Exception("Fail to get meal plan!"))) }
                }
            }
    }

    fun resetGetMealResponse() {
        _uiState.update { it.copy(getMealPlanResponse = null) }
    }
}
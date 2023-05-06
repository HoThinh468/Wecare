package com.vn.wecare.feature.food.addmeal.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.addmeal.data.MealsApi
import com.vn.wecare.feature.food.addmeal.data.MealsByNutrients
import com.vn.wecare.feature.food.addmeal.ui.AddMealFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddMealUiState(
    val getMealsDataResponse: Response<Boolean>? = null,
    val breakfastMealList: List<MealsByNutrients> = emptyList()
)

class AddMealViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AddMealUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getMealsByNutrients()
    }

    private fun getMealsByNutrients() = viewModelScope.launch {
        _uiState.update { it.copy(getMealsDataResponse = Response.Loading) }
        try {
            val res = MealsApi.getMealsByNutrients.getMealsByNutrients(
                maxCalories = 300, minCalories = 100, number = 10
            )
            _uiState.update {
                it.copy(breakfastMealList = res, getMealsDataResponse = Response.Success(true))
            }
        } catch (e: Exception) {
            Log.d(AddMealFragment.addMealTag, "Get meal fail due to ${e.message}")
            _uiState.update {
                it.copy(
                    breakfastMealList = emptyList(), getMealsDataResponse = Response.Error(null)
                )
            }
        }
    }

    fun resetGetDataResponse() {
        _uiState.update {
            it.copy(getMealsDataResponse = null)
        }
    }
}
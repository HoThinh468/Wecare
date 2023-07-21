package com.vn.wecare.feature.food.mealdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.model.MealRecipe
import com.vn.wecare.feature.food.data.repository.MealsRecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MealDetailUiState(
    val mealRecipe: MealRecipe = MealRecipe(), val getMealResponse: Response<Boolean>? = null
)

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val repo: MealsRecipeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MealDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun getMealRecipeWithId(mealId: Long) = viewModelScope.launch {
        _uiState.update { it.copy(getMealResponse = Response.Loading) }
        repo.getMealsRecipeWithMealId(mealId).collect { res ->
            if (res is Response.Success) {
                _uiState.update {
                    it.copy(
                        mealRecipe = res.data, getMealResponse = Response.Success(true)
                    )
                }
                Log.d("Meal recipe","${res.data}")
            } else {
                _uiState.update { it.copy(getMealResponse = Response.Error(null)) }
            }
        }
    }

    fun resetGetMealResponse() {
        _uiState.update { it.copy(getMealResponse = null) }
    }
}
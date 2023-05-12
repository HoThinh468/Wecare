package com.vn.wecare.feature.food.addmeal.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.addmeal.data.model.MealByNutrients
import com.vn.wecare.feature.food.addmeal.data.MealsRepository
import com.vn.wecare.feature.food.addmeal.data.model.MealTypeKey
import com.vn.wecare.feature.food.addmeal.ui.AddMealFragment
import com.vn.wecare.feature.food.nutrition.WecareCaloriesObject
import com.vn.wecare.feature.food.nutrition.usecase.CalculateNutrientsIndexUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class AddMealUiState(
    val insertMealRecordResponse: Response<Boolean>? = null,
    val breakfastMealList: List<MealByNutrients> = emptyList()
)

@HiltViewModel
class AddMealViewModel @Inject constructor(
    private val repository: MealsRepository,
    private val calculateNutrientsIndexUsecase: CalculateNutrientsIndexUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddMealUiState())
    val uiState = _uiState.asStateFlow()

    private val caloriesForBreakfast = WecareCaloriesObject.getInstance().caloriesOfBreakfast
    private val caloriesForLunch = WecareCaloriesObject.getInstance().caloriesOfLunch
    private val caloriesForSnack = WecareCaloriesObject.getInstance().caloriesOfSnack
    private val caloriesForDinner = WecareCaloriesObject.getInstance().caloriesOfDinner

    fun getBreakfastMealsByNutrients(): Flow<PagingData<MealByNutrients>> =
        repository.getMealsByNutrients(
            caloriesForBreakfast,
            getMinCalories(caloriesForBreakfast),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForBreakfast),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForBreakfast),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForBreakfast),
        ).cachedIn(viewModelScope)

    fun getLunchMealsByNutrients(): Flow<PagingData<MealByNutrients>> =
        repository.getMealsByNutrients(
            caloriesForLunch,
            getMinCalories(caloriesForLunch),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForLunch),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForLunch),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForLunch),
        ).cachedIn(viewModelScope)

    fun getSnackMealsByNutrients(): Flow<PagingData<MealByNutrients>> =
        repository.getMealsByNutrients(
            caloriesForSnack,
            getMinCalories(caloriesForSnack),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForSnack),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForSnack),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForSnack),
        ).cachedIn(viewModelScope)

    fun getDinnerMealsByNutrients(): Flow<PagingData<MealByNutrients>> =
        repository.getMealsByNutrients(
            caloriesForDinner,
            getMinCalories(caloriesForDinner),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForDinner),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForDinner),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForDinner),
        ).cachedIn(viewModelScope)

    fun insertMealRecord(dateTime: Calendar, mealTypeKey: MealTypeKey, meal: MealByNutrients) =
        viewModelScope.launch {
            _uiState.update { it.copy(insertMealRecordResponse = Response.Loading) }
            _uiState.update { it.copy() }
            repository.insertMeal(dateTime, mealTypeKey, meal).collect { res ->
                when (res) {
                    Response.Success(true) -> {
                        _uiState.update {
                            it.copy(insertMealRecordResponse = res)
                        }
                    }

                    else -> {
                        _uiState.update {
                            it.copy(insertMealRecordResponse = res)
                        }
                    }
                }
            }
        }

    private fun getMinCalories(maxCalories: Int): Int = maxCalories / 3
}
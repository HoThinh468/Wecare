package com.vn.wecare.feature.food.addmeal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.addmeal.data.MealsByNutrients
import com.vn.wecare.feature.food.addmeal.data.MealsRepository
import com.vn.wecare.feature.food.nutrition.WecareCaloriesObject
import com.vn.wecare.feature.food.nutrition.usecase.CalculateNutrientsIndexUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class AddMealUiState(
    val getMealsDataResponse: Response<Boolean>? = null,
    val breakfastMealList: List<MealsByNutrients> = emptyList()
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

    fun getBreakfastMealsByNutrients(): Flow<PagingData<MealsByNutrients>> =
        repository.getMealsByNutrients(
            caloriesForBreakfast,
            caloriesForBreakfast - 100,
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForBreakfast),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForBreakfast),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForBreakfast),
        ).cachedIn(viewModelScope)

    fun getLunchMealsByNutrients(): Flow<PagingData<MealsByNutrients>> =
        repository.getMealsByNutrients(
            caloriesForLunch,
            caloriesForLunch - 100,
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForLunch),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForLunch),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForLunch),
        ).cachedIn(viewModelScope)

    fun getSnackMealsByNutrients(): Flow<PagingData<MealsByNutrients>> =
        repository.getMealsByNutrients(
            caloriesForSnack,
            caloriesForSnack - 100,
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForSnack),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForSnack),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForSnack),
        ).cachedIn(viewModelScope)

    fun getDinnerMealsByNutrients(): Flow<PagingData<MealsByNutrients>> = repository.getMealsByNutrients(
        caloriesForDinner,
        caloriesForDinner - 100,
        calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForDinner),
        calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForDinner),
        calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForDinner),
    ).cachedIn(viewModelScope)
}
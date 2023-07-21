package com.vn.wecare.feature.food.addmeal.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.model.MealRecipe
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.data.model.toMealRecordModel
import com.vn.wecare.feature.food.data.repository.MealsRecipeRepository
import com.vn.wecare.feature.food.usecase.GetMealsWithDayIdUsecase
import com.vn.wecare.feature.food.usecase.InsertMealRecordUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class AddMealUiState(
    val insertMealRecordResponse: Response<Boolean>? = null,
    val currentChosenMeal: MealRecipe? = null
)

@HiltViewModel
class AddMealViewModel @Inject constructor(
//    private val repository: MealsRepository,
    private val mealsRecipeRepository: MealsRecipeRepository,
//    private val calculateNutrientsIndexUsecase: CalculateNutrientsIndexUsecase,
    private val getMealsWithDayIdUsecase: GetMealsWithDayIdUsecase,
    private val insertMealRecordUsecase: InsertMealRecordUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddMealUiState())
    val uiState = _uiState.asStateFlow()

    private val _breakfastMeals = MutableStateFlow(emptyList<MealRecipe>())
    val breakfastMeals = _breakfastMeals.asStateFlow()

    private val _lunchMeals = MutableStateFlow(emptyList<MealRecipe>())
    val lunchMeals = _lunchMeals.asStateFlow()

    private val _snackMeals = MutableStateFlow(emptyList<MealRecipe>())
    val snackMeals = _snackMeals.asStateFlow()

    private val _dinnerMeals = MutableStateFlow(emptyList<MealRecipe>())
    val dinnerMeals = _dinnerMeals.asStateFlow()

    var getMealsResponse by mutableStateOf<Response<Boolean>?>(null)

    private var currentDayBreakfastRecord = mutableListOf<MealRecordModel>()
    private var currentDayLunchRecord = mutableListOf<MealRecordModel>()
    private var currentDaySnackRecord = mutableListOf<MealRecordModel>()
    private var currentDayDinnerRecord = mutableListOf<MealRecordModel>()

    init {
        getRecommendedMealsForBreakfast()
        getRecommendedMealsForLunch()
        getRecommendedMealsForSnack()
        getRecommendedMealsForDinner()
    }

    fun getMealsOfAllTypeList() {
        val calendar = Calendar.getInstance()
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        currentDayBreakfastRecord.addAll(
            getMealListByType(dayOfMonth, month, year, MealTypeKey.BREAKFAST)
        )

        currentDayLunchRecord.addAll(
            getMealListByType(dayOfMonth, month, year, MealTypeKey.LUNCH)
        )

        currentDaySnackRecord.addAll(
            getMealListByType(dayOfMonth, month, year, MealTypeKey.SNACK)
        )

        currentDayDinnerRecord.addAll(
            getMealListByType(dayOfMonth, month, year, MealTypeKey.DINNER)
        )
    }

    fun resetMealListOfAllType() {
        currentDayBreakfastRecord.clear()
        currentDayLunchRecord.clear()
        currentDaySnackRecord.clear()
        currentDayDinnerRecord.clear()
    }

    private fun getRecommendedMealsForBreakfast() = viewModelScope.launch {
        getMealsResponse = Response.Loading
        mealsRecipeRepository.getMealsRecipeWithMealTypeKey(MealTypeKey.BREAKFAST).collect { res ->
            getMealsResponse = if (res is Response.Success) {
                _breakfastMeals.update { res.data }
                Response.Success(true)
            } else {
                _breakfastMeals.update { emptyList() }
                Response.Error(null)
            }
        }
    }

    private fun getRecommendedMealsForLunch() = viewModelScope.launch {
        getMealsResponse = Response.Loading
        mealsRecipeRepository.getMealsRecipeWithMealTypeKey(MealTypeKey.LUNCH).collect { res ->
            getMealsResponse = if (res is Response.Success) {
                _lunchMeals.update { res.data }
                Response.Success(true)
            } else {
                _lunchMeals.update { emptyList() }
                Response.Error(null)
            }
        }
    }

    private fun getRecommendedMealsForSnack() = viewModelScope.launch {
        getMealsResponse = Response.Loading
        mealsRecipeRepository.getMealsRecipeWithMealTypeKey(MealTypeKey.SNACK).collect { res ->
            getMealsResponse = if (res is Response.Success) {
                _snackMeals.update { res.data }
                Response.Success(true)
            } else {
                _snackMeals.update { emptyList() }
                Response.Error(null)
            }
        }
    }

    private fun getRecommendedMealsForDinner() = viewModelScope.launch {
        getMealsResponse = Response.Loading
        mealsRecipeRepository.getMealsRecipeWithMealTypeKey(MealTypeKey.DINNER).collect { res ->
            getMealsResponse = if (res is Response.Success) {
                _dinnerMeals.update { res.data }
                Response.Success(true)
            } else {
                _dinnerMeals.update { emptyList() }
                Response.Error(null)
            }
        }
    }

    fun updateCurrentChosenMeal(meal: MealRecipe) {
        _uiState.update {
            it.copy(currentChosenMeal = meal)
        }
    }

    fun insertMealRecord(dateTime: Calendar, mealTypeKey: MealTypeKey, meal: MealRecipe) =
        viewModelScope.launch {
            _uiState.update { it.copy(insertMealRecordResponse = Response.Loading) }
            insertMealRecordUsecase.insertMealRecord(dateTime, mealTypeKey, meal).collect { res ->
                _uiState.update {
                    it.copy(insertMealRecordResponse = res)
                }
                if (res is Response.Success) {
                    updateMealListByType(mealTypeKey, meal.toMealRecordModel())
                }
            }
        }

    private fun getMealListByType(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ): List<MealRecordModel> {
        val result = mutableListOf<MealRecordModel>()
        viewModelScope.launch {
            getMealsWithDayIdUsecase.getMealOfEachTypeInDayWithDayId(
                dayOfMonth, month, year, mealTypeKey
            ).collect { res ->
                if (res is Response.Success) {
                    for (item in res.data) {
                        result.add(item)
                    }
                }
            }
        }
        return result
    }

    fun resetInsertMealRecordResponse() {
        _uiState.update { it.copy(insertMealRecordResponse = null, currentChosenMeal = null) }
    }

    private fun updateMealListByType(mealTypeKey: MealTypeKey, mealRecordModel: MealRecordModel) {
        when (mealTypeKey) {
            MealTypeKey.BREAKFAST -> currentDayBreakfastRecord.add(mealRecordModel)
            MealTypeKey.LUNCH -> currentDayLunchRecord.add(mealRecordModel)
            MealTypeKey.SNACK -> currentDaySnackRecord.add(mealRecordModel)
            else -> currentDayDinnerRecord.add(mealRecordModel)
        }
    }
}
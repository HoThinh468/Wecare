package com.vn.wecare.feature.food.addmeal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.feature.food.data.MealsRepository
import com.vn.wecare.feature.food.data.model.MealByNutrients
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.data.model.toMealByNutrients
import com.vn.wecare.feature.food.data.model.toRecordModel
import com.vn.wecare.feature.food.usecase.CalculateNutrientsIndexUsecase
import com.vn.wecare.feature.food.usecase.GetMealsWithDayIdUsecase
import com.vn.wecare.feature.food.usecase.InsertMealRecordUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class AddMealUiState(
    val insertMealRecordResponse: Response<Boolean>? = null,
    val currentChosenMeal: MealByNutrients? = null
)

@HiltViewModel
class AddMealViewModel @Inject constructor(
    private val repository: MealsRepository,
    private val calculateNutrientsIndexUsecase: CalculateNutrientsIndexUsecase,
    private val getMealsWithDayIdUsecase: GetMealsWithDayIdUsecase,
    private val insertMealRecordUsecase: InsertMealRecordUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddMealUiState())
    val uiState = _uiState.asStateFlow()

    private val caloriesForBreakfast = WecareCaloriesObject.getInstance().caloriesOfBreakfast
    private val caloriesForLunch = WecareCaloriesObject.getInstance().caloriesOfLunch
    private val caloriesForSnack = WecareCaloriesObject.getInstance().caloriesOfSnack
    private val caloriesForDinner = WecareCaloriesObject.getInstance().caloriesOfDinner

    private var currentDayBreakfastRecord = mutableListOf<MealRecordModel>()
    private var currentDayLunchRecord = mutableListOf<MealRecordModel>()
    private var currentDaySnackRecord = mutableListOf<MealRecordModel>()
    private var currentDayDinnerRecord = mutableListOf<MealRecordModel>()

    var breakFastMealList: Flow<PagingData<MealByNutrients>>

    init {
        breakFastMealList = getBreakfastMealsByNutrients()
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

    private fun getBreakfastMealsByNutrients(): Flow<PagingData<MealByNutrients>> {
        return repository.getMealsByNutrientsWithPagingSource(
            caloriesForBreakfast,
            getMinCalories(caloriesForBreakfast),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForBreakfast),
            calculateNutrientsIndexUsecase.getFatIndexInGram(caloriesForBreakfast),
            calculateNutrientsIndexUsecase.getCarbIndexInGram(caloriesForBreakfast),
        ).map { pagingData ->
            pagingData.map {
                it.toMealByNutrients()
            }
        }.cachedIn(viewModelScope)
    }

    fun getLunchMealsByNutrients(): Flow<PagingData<MealByNutrients>> {
        return repository.getMealsByNutrientsWithPagingSource(
            caloriesForLunch,
            getMinCalories(caloriesForLunch),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForLunch),
            calculateNutrientsIndexUsecase.getFatIndexInGram(caloriesForLunch),
            calculateNutrientsIndexUsecase.getCarbIndexInGram(caloriesForLunch),
        ).map { pagingData ->
            pagingData.map {
                it.toMealByNutrients()
            }
        }.cachedIn(viewModelScope)
    }

    fun getSnackMealsByNutrients(): Flow<PagingData<MealByNutrients>> {
        return repository.getMealsByNutrientsWithPagingSource(
            caloriesForSnack,
            getMinCalories(caloriesForSnack),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForSnack),
            calculateNutrientsIndexUsecase.getFatIndexInGram(caloriesForSnack),
            calculateNutrientsIndexUsecase.getCarbIndexInGram(caloriesForSnack),
        ).map { pagingData ->
            pagingData.map {
                it.toMealByNutrients()
            }
        }.cachedIn(viewModelScope)
    }

    fun getDinnerMealsByNutrients(): Flow<PagingData<MealByNutrients>> {
        return repository.getMealsByNutrientsWithPagingSource(
            caloriesForDinner,
            getMinCalories(caloriesForDinner),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForDinner),
            calculateNutrientsIndexUsecase.getFatIndexInGram(caloriesForDinner),
            calculateNutrientsIndexUsecase.getCarbIndexInGram(caloriesForDinner),
        ).map { pagingData ->
            pagingData.map {
                it.toMealByNutrients()
            }
        }.cachedIn(viewModelScope)
    }

    fun updateCurrentChosenMeal(meal: MealByNutrients) {
        _uiState.update {
            it.copy(currentChosenMeal = meal)
        }
    }

    fun insertMealRecord(dateTime: Calendar, mealTypeKey: MealTypeKey, meal: MealByNutrients) =
        viewModelScope.launch {
            _uiState.update { it.copy(insertMealRecordResponse = Response.Loading) }
            insertMealRecordUsecase.insertMealRecord(dateTime, mealTypeKey, meal).collect { res ->
                _uiState.update {
                    it.copy(insertMealRecordResponse = res)
                }
                if (res is Response.Success) {
                    updateMealListByType(mealTypeKey, meal.toRecordModel())
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

    private fun getMinCalories(maxCalories: Int): Int = maxCalories / 3
}
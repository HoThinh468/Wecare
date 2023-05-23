package com.vn.wecare.feature.food.addmeal.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.feature.food.addmeal.ui.AddMealFragment
import com.vn.wecare.feature.food.data.MealsRepository
import com.vn.wecare.feature.food.data.model.MealByNutrients
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.data.model.toModel
import com.vn.wecare.feature.food.usecase.CalculateNutrientsIndexUsecase
import com.vn.wecare.feature.food.usecase.GetMealsWithDayIdUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddMealUiState())
    val uiState = _uiState.asStateFlow()

    private val caloriesForBreakfast = WecareCaloriesObject.getInstance().caloriesOfBreakfast
    private val caloriesForLunch = WecareCaloriesObject.getInstance().caloriesOfLunch
    private val caloriesForSnack = WecareCaloriesObject.getInstance().caloriesOfSnack
    private val caloriesForDinner = WecareCaloriesObject.getInstance().caloriesOfDinner

    private val currentDayBreakfastRecord = mutableListOf<MealRecordModel>()
    private var currentDayLunchRecord = mutableListOf<MealRecordModel>()
    private var currentDaySnackRecord = mutableListOf<MealRecordModel>()
    private var currentDayDinnerRecord = mutableListOf<MealRecordModel>()

    var breakFastMealList: Flow<PagingData<MealByNutrients>>

    var searchText by mutableStateOf("")
    fun onSearchTextChanged(newVal: String) {
        searchText = newVal
//        searchMeal(newVal)
    }

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

    private fun getBreakfastMealsByNutrients(): Flow<PagingData<MealByNutrients>> {
        return repository.getMealsByNutrients(
            caloriesForBreakfast,
            getMinCalories(caloriesForBreakfast),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForBreakfast),
            calculateNutrientsIndexUsecase.getFatIndexInGram(caloriesForBreakfast),
            calculateNutrientsIndexUsecase.getCarbIndexInGram(caloriesForBreakfast),
        ).cachedIn(viewModelScope)
    }

    fun getLunchMealsByNutrients(): Flow<PagingData<MealByNutrients>> {
        return repository.getMealsByNutrients(
            caloriesForLunch,
            getMinCalories(caloriesForLunch),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForLunch),
            calculateNutrientsIndexUsecase.getFatIndexInGram(caloriesForLunch),
            calculateNutrientsIndexUsecase.getCarbIndexInGram(caloriesForLunch),
        ).cachedIn(viewModelScope)
    }

    fun getSnackMealsByNutrients(): Flow<PagingData<MealByNutrients>> {
        return repository.getMealsByNutrients(
            caloriesForSnack,
            getMinCalories(caloriesForSnack),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForSnack),
            calculateNutrientsIndexUsecase.getFatIndexInGram(caloriesForSnack),
            calculateNutrientsIndexUsecase.getCarbIndexInGram(caloriesForSnack),
        ).cachedIn(viewModelScope)
    }

    fun getDinnerMealsByNutrients(): Flow<PagingData<MealByNutrients>> {
        return repository.getMealsByNutrients(
            caloriesForDinner,
            getMinCalories(caloriesForDinner),
            calculateNutrientsIndexUsecase.getProteinIndexInGram(caloriesForDinner),
            calculateNutrientsIndexUsecase.getFatIndexInGram(caloriesForDinner),
            calculateNutrientsIndexUsecase.getCarbIndexInGram(caloriesForDinner),
        ).cachedIn(viewModelScope)
    }

    fun updateCurrentChosenMeal(meal: MealByNutrients) {
        _uiState.update {
            it.copy(currentChosenMeal = meal)
        }
    }

    fun insertMealRecord(dateTime: Calendar, mealTypeKey: MealTypeKey, meal: MealByNutrients) {
        val isMealExist = when (mealTypeKey) {
            MealTypeKey.BREAKFAST -> currentDayBreakfastRecord.contains(meal.toModel())
            MealTypeKey.LUNCH -> currentDayLunchRecord.contains(meal.toModel())
            MealTypeKey.SNACK -> currentDaySnackRecord.contains(meal.toModel())
            else -> currentDayDinnerRecord.contains(meal.toModel())
        }

        if (isMealExist) {
            val e = Exception("This item has been added, check your list")
            _uiState.update { it.copy(insertMealRecordResponse = Response.Error(e)) }
        } else {
            viewModelScope.launch {
                _uiState.update { it.copy(insertMealRecordResponse = Response.Loading) }
                repository.insertMeal(dateTime, mealTypeKey, meal).collect { res ->
                    _uiState.update {
                        it.copy(insertMealRecordResponse = res)
                    }
                    if (res is Response.Success) {
                        updateMealListByType(mealTypeKey, meal.toModel())
                    }
                }
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
                if (res is Response.Success && res.data != null) {
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

//    private fun searchMeal(searchText: String) = viewModelScope.launch {
//        breakFastMealList.collect {
//            Log.d(AddMealFragment.addMealTag, "initial meal list: $it")
//        }
//        breakFastMealList = if (searchText.isNotEmpty()) {
//            breakFastMealList.map {
//                it.filter { meal ->
//                    isMealNameMatchSearch(meal.title, searchText)
//                }
//            }
//        } else {
//            breakFastMealList
//        }
//        breakFastMealList.collect {
//            Log.d(AddMealFragment.addMealTag, "after filter meal list: ${it.toString()}")
//        }
//    }

    private fun updateMealListByType(mealTypeKey: MealTypeKey, mealRecordModel: MealRecordModel) {
        when (mealTypeKey) {
            MealTypeKey.BREAKFAST -> currentDayBreakfastRecord.add(mealRecordModel)
            MealTypeKey.LUNCH -> currentDayLunchRecord.add(mealRecordModel)
            MealTypeKey.SNACK -> currentDaySnackRecord.add(mealRecordModel)
            else -> currentDayDinnerRecord.add(mealRecordModel)
        }
    }

    private fun getMinCalories(maxCalories: Int): Int = maxCalories / 3

    private fun isMealNameMatchSearch(mealName: String, searchText: String): Boolean {
        return mealName.contains(searchText)
    }
}
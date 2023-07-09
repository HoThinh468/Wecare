package com.vn.wecare.feature.food.dinner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.feature.food.common.MealUiState
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.usecase.CalculateNutrientsIndexUsecase
import com.vn.wecare.feature.food.usecase.GetMealsWithDayIdUsecase
import com.vn.wecare.feature.food.usecase.UpdateMealRecordQuantityUsecase
import com.vn.wecare.utils.getMonthPrefix
import com.vn.wecare.utils.getNutrientIndexFromString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DinnerViewModel @Inject constructor(
    private val getMealsWithDayIdUsecase: GetMealsWithDayIdUsecase,
    private val calculateNutrientsIndexUsecase: CalculateNutrientsIndexUsecase,
    private val updateMealRecordQuantityUsecase: UpdateMealRecordQuantityUsecase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MealUiState())
    val uiState = _uiState.asStateFlow()

    private var mDayOfMonth = 0
    private var mMonth = 0
    private var mYear = 0

    init {
        initUiState()
    }

    private fun initUiState() {
        val calendar = Calendar.getInstance()
        mDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        mMonth = calendar.get(Calendar.MONTH)
        mYear = calendar.get(Calendar.YEAR)
        updateDateTime(mDayOfMonth, mMonth, mYear)
        getDinnerMealList(mDayOfMonth, mMonth, mYear)
        updateTargetNutrientsInformation()
        checkIfAddMealIsEnable()
    }

    fun reUpdateUiState() {
        updateDateTime(mDayOfMonth, mMonth, mYear)
        getDinnerMealList(mDayOfMonth, mMonth, mYear)
        updateTargetNutrientsInformation()
    }

    fun onDateChangeListener(dayOfMonth: Int, month: Int, year: Int) {
        updateDateTime(dayOfMonth, month, year)
        getDinnerMealList(dayOfMonth, month, year)
        mDayOfMonth = dayOfMonth
        mMonth = month
        mYear = year
        checkIfAddMealIsEnable()
    }

    fun onMealRecordItemPlusClick(mealRecordModel: MealRecordModel) = viewModelScope.launch {
        _uiState.update {
            it.copy(updateMealRecordResponse = Response.Loading)
        }
        updateMealRecordQuantityUsecase.plusMealRecord(
            mDayOfMonth, mMonth, mYear, MealTypeKey.DINNER, mealRecordModel
        ).collect { res ->
            _uiState.update {
                it.copy(updateMealRecordResponse = res)
            }
        }
        getDinnerMealList(mDayOfMonth, mMonth, mYear)
    }

    fun onMealRecordItemMinusClick(mealRecordModel: MealRecordModel) = viewModelScope.launch {
        _uiState.update {
            it.copy(updateMealRecordResponse = Response.Loading)
        }
        updateMealRecordQuantityUsecase.minusMealRecord(
            mDayOfMonth, mMonth, mYear, MealTypeKey.DINNER, mealRecordModel
        ).collect { res ->
            _uiState.update {
                it.copy(updateMealRecordResponse = res)
            }
        }
        getDinnerMealList(mDayOfMonth, mMonth, mYear)
    }

    fun resetUpdateRecordResponse() {
        _uiState.update { it.copy(updateMealRecordResponse = null) }
    }

    private fun updateTargetNutrientsInformation() = viewModelScope.launch {
        val caloriesObj = WecareCaloriesObject.getInstanceFlow()
        caloriesObj.collect { obj ->
            _uiState.update {
                it.copy(
                    targetCalories = obj.caloriesOfDinner,
                    targetProtein = calculateNutrientsIndexUsecase.getProteinIndexInGram(obj.caloriesOfDinner),
                    targetFat = calculateNutrientsIndexUsecase.getFatIndexInGram(obj.caloriesOfDinner),
                    targetCarbs = calculateNutrientsIndexUsecase.getCarbIndexInGram(obj.caloriesOfDinner)
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

    private fun getDinnerMealList(dayOfMonth: Int, month: Int, year: Int) = viewModelScope.launch {
        _uiState.update { it.copy(getMealsResponse = Response.Loading) }
        getMealsWithDayIdUsecase.getMealOfEachTypeInDayWithDayId(
            dayOfMonth, month, year, MealTypeKey.DINNER
        ).collect { res ->
            if (res is Response.Success) {
                _uiState.update { it.copy(mealRecords = res.data) }
                updateNutritionIndex(res.data)
                _uiState.update { it.copy(getMealsResponse = Response.Success(true)) }
            } else {
                _uiState.update { it.copy(getMealsResponse = Response.Error(null)) }
            }
        }
    }

    private fun updateNutritionIndex(recordList: List<MealRecordModel>) {
        if (recordList.isNotEmpty()) {
            var totalCalories = 0
            var totalProtein = 0
            var totalFat = 0
            var totalCarbs = 0
            for (item in recordList) {
                totalCalories += item.calories * item.quantity
                totalProtein += item.protein.getNutrientIndexFromString() * item.quantity
                totalFat += item.fat.getNutrientIndexFromString() * item.quantity
                totalCarbs += item.carbs.getNutrientIndexFromString() * item.quantity
            }
            _uiState.update {
                it.copy(
                    calories = totalCalories,
                    protein = totalProtein,
                    fat = totalFat,
                    carbs = totalCarbs,
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

    private fun checkIfAddMealIsEnable() {
        val calendar = Calendar.getInstance()
        val isEnable =
            calendar.get(Calendar.DAY_OF_MONTH) == mDayOfMonth && calendar.get(Calendar.MONTH) == mMonth && mYear == calendar.get(
                Calendar.YEAR
            )
        _uiState.update { it.copy(isAddMealEnable = isEnable) }
    }
}
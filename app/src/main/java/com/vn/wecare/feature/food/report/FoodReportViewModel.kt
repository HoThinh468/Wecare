package com.vn.wecare.feature.food.report

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.feature.food.data.MealsRepository
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.usecase.GetMealsWithDayIdUsecase
import com.vn.wecare.utils.WecareUserConstantValues.NUMBER_OF_DAYS_IN_WEEK
import com.vn.wecare.utils.getDayFormatWithOnlyMonthPrefix
import com.vn.wecare.utils.getDayFormatWithYear
import com.vn.wecare.utils.getDayOfWeekPrefix
import com.vn.wecare.utils.getFirstDayOfWeekWithGivenDate
import com.vn.wecare.utils.getLastDayOfWeekWithGivenDay
import com.vn.wecare.utils.getListOfDayWithStartAndEndDay
import com.vn.wecare.utils.getNutrientIndexFromString
import com.vn.wecare.utils.getProgressInFloatWithIntInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class FoodDayReport(
    val progress: Float, val caloriesInTake: Int, val dayOfWeek: String
)

data class FoodReportUiState(
    val isLoadingData: Response<Boolean>? = null,
    val averageAmount: Int = 0,
    val firstDayOfWeek: String = "",
    val lastDayOfWeek: String = "",
    val dayReportList: List<FoodDayReport> = emptyList(),
    val isAbleToShowBarChart: Boolean = false,
    val isNextClickEnable: Boolean = false,
)

data class NutrientReportUiState(
    val proteinAmount: Int = 0,
    val fatAmount: Int = 0,
    val carbsAmount: Int = 0,
    val breakfastCalories: Int = 0,
    val breakfastProgress: Float = 0f,
    val lunchCalories: Int = 0,
    val lunchProgress: Float = 0f,
    val snackCalories: Int = 0,
    val snackProgress: Float = 0f,
    val dinnerCalories: Int = 0,
    val dinnerProgress: Float = 0f
)

@HiltViewModel
class FoodReportViewModel @Inject constructor(
    private val getMealsWithDayIdUsecase: GetMealsWithDayIdUsecase,
    private val mealsRepository: MealsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FoodReportUiState())
    val uiState = _uiState.asStateFlow()

    private val _nutrientReportUiState = MutableStateFlow(NutrientReportUiState())
    val nutrientReportUiState = _nutrientReportUiState.asStateFlow()

    private var desiredViewDay = mutableStateOf(LocalDate.now())

    fun initReportView() {
        updateFirstAndLastDayOfWeekWithGivenDay(LocalDate.now())
        updateBarChartWithCalories()
        updateNutrientsDetail()
        updateBreakfastNutrients()
        updateLunchNutrients()
        updateSnackNutrients()
        updateDinnerNutrients()
    }

    fun onPreviousWeekClick() {
        desiredViewDay.value = desiredViewDay.value.minusDays(7)
        updateBarChartWithCalories()
        updateBreakfastNutrients()
        updateLunchNutrients()
        updateSnackNutrients()
        updateDinnerNutrients()
        updateNutrientsDetail()
        updateFirstAndLastDayOfWeekWithGivenDay(desiredViewDay.value)
        updateIfViewNextWeekEnable()
    }

    fun onNextWeekClick() {
        desiredViewDay.value = desiredViewDay.value.plusDays(7)
        updateBarChartWithCalories()
        updateBreakfastNutrients()
        updateLunchNutrients()
        updateSnackNutrients()
        updateDinnerNutrients()
        updateNutrientsDetail()
        updateFirstAndLastDayOfWeekWithGivenDay(desiredViewDay.value)
        updateIfViewNextWeekEnable()
    }

    private fun updateFirstAndLastDayOfWeekWithGivenDay(date: LocalDate) {
        updateFirstAndLastDayOfWeekWithFirstAndLastDay(
            getFirstDayOfWeekWithGivenDate(date), getLastDayOfWeekWithGivenDay(date)
        )
    }

    private fun updateIfViewNextWeekEnable() {
        _uiState.update {
            it.copy(
                isNextClickEnable = desiredViewDay.value.dayOfYear != LocalDate.now().dayOfYear
            )
        }
    }

    private fun updateBarChartWithCalories() {
        _uiState.update { it.copy(isLoadingData = Response.Loading) }
        val mealRecordDayList = arrayListOf<FoodDayReport>()
        val dayOfWeekList = getListOfDaysInWeek()
        Log.d(FoodReportFragment.foodReportTag, "day of week list: $dayOfWeekList")
        viewModelScope.launch {
            repeat(dayOfWeekList.size) { i ->
                val day = dayOfWeekList[i]
                getCaloriesOfDay(day).collect { res ->
                    val dayReport = FoodDayReport(
                        progress = getProgressInFloatWithIntInput(
                            res, WecareCaloriesObject.getInstance().caloriesInEachDay
                        ), caloriesInTake = res, dayOfWeek = getDayOfWeekPrefix(i + 1)
                    )
                    mealRecordDayList.add(dayReport)
                }
            }
            checkWhetherShowBarChartIsEnable(mealRecordDayList)
            updateAverageCaloriesAmount(mealRecordDayList)
            _uiState.update {
                it.copy(dayReportList = mealRecordDayList, isLoadingData = Response.Success(true))
            }
        }
    }

    private fun checkWhetherShowBarChartIsEnable(dayReportList: List<FoodDayReport>) {
        var isDefaultValue = true
        repeat(dayReportList.size) { i ->
            val item = dayReportList[i]
            if (item.caloriesInTake != 0) {
                isDefaultValue = false
                return@repeat
            }
        }
        _uiState.update { it.copy(isAbleToShowBarChart = !isDefaultValue) }
    }

    private fun updateFirstAndLastDayOfWeekWithFirstAndLastDay(
        firstDay: LocalDate, lastDay: LocalDate
    ) {
        val lastDayString = getDayFormatWithYear(lastDay)
        val firstDayString = if (firstDay.year == lastDay.year) {
            if (firstDay.month == lastDay.month) {
                firstDay.dayOfMonth.toString()
            } else {
                getDayFormatWithOnlyMonthPrefix(firstDay)
            }
        } else {
            getDayFormatWithYear(firstDay)
        }
        _uiState.update {
            it.copy(
                firstDayOfWeek = firstDayString, lastDayOfWeek = lastDayString
            )
        }
    }

    private suspend fun getListMealOfDay(day: LocalDate) = combine(
        getMealsWithDayIdUsecase.getMealOfEachTypeInDayWithDayId(
            day.dayOfMonth, day.monthValue - 1, day.year, MealTypeKey.BREAKFAST
        ), getMealsWithDayIdUsecase.getMealOfEachTypeInDayWithDayId(
            day.dayOfMonth, day.monthValue - 1, day.year, MealTypeKey.LUNCH
        ), getMealsWithDayIdUsecase.getMealOfEachTypeInDayWithDayId(
            day.dayOfMonth, day.monthValue - 1, day.year, MealTypeKey.SNACK
        ), getMealsWithDayIdUsecase.getMealOfEachTypeInDayWithDayId(
            day.dayOfMonth, day.monthValue - 1, day.year, MealTypeKey.DINNER
        )
    ) { breakfast, lunch, snack, dinner ->
        val recordList = arrayListOf<MealRecordModel>()
        if (breakfast is Response.Success && lunch is Response.Success && snack is Response.Success && dinner is Response.Success) {
            recordList.apply {
                addAll(breakfast.data)
                addAll(lunch.data)
                addAll(snack.data)
                addAll(dinner.data)
            }
        }
        recordList
    }

    private fun getCaloriesOfDay(day: LocalDate): Flow<Int> = flow {
        getListMealOfDay(day).collect { list ->
            var calories = 0
            for (i in list) {
                calories += i.calories * i.quantity
            }
            emit(calories)
        }
    }

    private fun getProteinOfDays(days: List<LocalDate>): Flow<Int> = flow {
        var protein = 0
        for (day in days) {
            getListMealOfDay(day).collect {
                for (i in it) {
                    protein += i.quantity * i.protein.getNutrientIndexFromString()
                }
            }
        }
        emit(protein)
    }

    private fun getFatOfDays(days: List<LocalDate>): Flow<Int> = flow {
        var fat = 0
        for (day in days) {
            getListMealOfDay(day).collect {
                for (i in it) {
                    fat += i.quantity * i.fat.getNutrientIndexFromString()
                }
            }
        }
        emit(fat)
    }

    private fun getCarbsOfDays(days: List<LocalDate>): Flow<Int> = flow {
        var carbs = 0
        for (day in days) {
            getListMealOfDay(day).collect {
                for (i in it) {
                    carbs += i.quantity * i.carbs.getNutrientIndexFromString()
                }
            }
        }
        emit(carbs)
    }

    private fun updateNutrientsDetail() = viewModelScope.launch {
        val days = getListOfDaysInWeek()
        combine(
            getProteinOfDays(days), getFatOfDays(days), getCarbsOfDays(days)
        ) { protein, fat, carbs ->
            val nutrients = arrayListOf<Int>()
            nutrients.apply {
                add(protein)
                add(fat)
                add(carbs)
            }
            nutrients
        }.collect { res ->
            _nutrientReportUiState.update {
                it.copy(proteinAmount = res[0], fatAmount = res[1], carbsAmount = res[2])
            }
        }
    }

    private fun updateBreakfastNutrients() = viewModelScope.launch {
        val days = getListOfDaysInWeek()
        var breakfastCalories = 0
        val caloriesOfBreakfast = WecareCaloriesObject.getInstance().caloriesOfBreakfast
        for (day in days) {
            mealsRepository.getMealOfEachTypeInDayWithDayId(
                day.dayOfMonth, day.monthValue - 1, day.year, MealTypeKey.BREAKFAST
            ).collect { res ->
                if (res is Response.Success) {
                    for (i in res.data) {
                        breakfastCalories += i.calories * i.quantity
                    }
                }
            }
        }
        _nutrientReportUiState.update {
            it.copy(
                breakfastCalories = breakfastCalories,
                breakfastProgress = breakfastCalories.toFloat() / caloriesOfBreakfast.times(
                    NUMBER_OF_DAYS_IN_WEEK
                )
            )
        }
    }

    private fun updateLunchNutrients() = viewModelScope.launch {
        val days = getListOfDaysInWeek()
        var lunchCalories = 0
        val caloriesOfLunch = WecareCaloriesObject.getInstance().caloriesOfLunch
        for (day in days) {
            mealsRepository.getMealOfEachTypeInDayWithDayId(
                day.dayOfMonth, day.monthValue - 1, day.year, MealTypeKey.LUNCH
            ).collect { res ->
                if (res is Response.Success) {
                    for (i in res.data) {
                        lunchCalories += i.calories * i.quantity
                    }
                }
            }
        }
        _nutrientReportUiState.update {
            it.copy(
                lunchCalories = lunchCalories,
                lunchProgress = lunchCalories.toFloat() / caloriesOfLunch.times(
                    NUMBER_OF_DAYS_IN_WEEK
                )
            )
        }
    }

    private fun updateSnackNutrients() = viewModelScope.launch {
        val days = getListOfDaysInWeek()
        var snackCalories = 0
        val caloriesOfSnack = WecareCaloriesObject.getInstance().caloriesOfSnack
        for (day in days) {
            mealsRepository.getMealOfEachTypeInDayWithDayId(
                day.dayOfMonth, day.monthValue - 1, day.year, MealTypeKey.SNACK
            ).collect { res ->
                if (res is Response.Success) {
                    for (i in res.data) {
                        snackCalories += i.calories * i.quantity
                    }
                }
            }
        }
        _nutrientReportUiState.update {
            it.copy(
                snackCalories = snackCalories,
                snackProgress = snackCalories.toFloat() / caloriesOfSnack.times(NUMBER_OF_DAYS_IN_WEEK)
            )
        }
    }

    private fun updateDinnerNutrients() = viewModelScope.launch {
        val days = getListOfDaysInWeek()
        var dinnerCalories = 0
        val caloriesOfDinner = WecareCaloriesObject.getInstance().caloriesOfDinner
        for (day in days) {
            mealsRepository.getMealOfEachTypeInDayWithDayId(
                day.dayOfMonth, day.monthValue - 1, day.year, MealTypeKey.DINNER
            ).collect { res ->
                if (res is Response.Success) {
                    for (i in res.data) {
                        dinnerCalories += i.calories * i.quantity
                    }
                }
            }
        }
        _nutrientReportUiState.update {
            it.copy(
                dinnerCalories = dinnerCalories,
                dinnerProgress = dinnerCalories.toFloat() / caloriesOfDinner.times(NUMBER_OF_DAYS_IN_WEEK)
            )
        }
    }

    private fun updateAverageCaloriesAmount(list: List<FoodDayReport>) {
        var totalCaloriesOfWeek = 0
        for (i in list) {
            totalCaloriesOfWeek += i.caloriesInTake
        }
        _uiState.update { it.copy(averageAmount = totalCaloriesOfWeek / NUMBER_OF_DAYS_IN_WEEK) }
    }

    private fun getListOfDaysInWeek(): List<LocalDate> {
        return getListOfDayWithStartAndEndDay(
            getFirstDayOfWeekWithGivenDate(desiredViewDay.value),
            getLastDayOfWeekWithGivenDay(desiredViewDay.value)
        )
    }
}
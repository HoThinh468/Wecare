package com.vn.wecare.feature.food.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.food.data.model.MealRecipe
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.data.model.toMealRecordModel
import com.vn.wecare.feature.food.data.repository.MealsRepository
import com.vn.wecare.feature.home.goal.usecase.UpdateGoalRecordUsecase
import com.vn.wecare.feature.home.step_count.usecase.CaloPerDay
import com.vn.wecare.feature.home.step_count.usecase.DashboardUseCase
import com.vn.wecare.utils.getNutrientIndexFromString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class InsertMealRecordUsecase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getMealsWithDayIdUsecase: GetMealsWithDayIdUsecase,
    private val repository: MealsRepository,
    private val updateGoalRecordUsecase: UpdateGoalRecordUsecase,
    private val dashboardUseCase: DashboardUseCase
) {
    private val currentDayBreakfastRecord = mutableListOf<MealRecordModel>()
    private val currentDayLunchRecord = mutableListOf<MealRecordModel>()
    private val currentDaySnackRecord = mutableListOf<MealRecordModel>()
    private val currentDayDinnerRecord = mutableListOf<MealRecordModel>()

    init {
        dashboardUseCase.getCaloPerDay()
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

    fun insertMealRecord(
        dateTime: Calendar, mealTypeKey: MealTypeKey, meal: MealRecipe
    ): Flow<Response<Boolean>> = flow {
        val recordModel = meal.toMealRecordModel()
        val isMealExist = when (mealTypeKey) {
            MealTypeKey.BREAKFAST -> currentDayBreakfastRecord.contains(recordModel)
            MealTypeKey.LUNCH -> currentDayLunchRecord.contains(recordModel)
            MealTypeKey.SNACK -> currentDaySnackRecord.contains(recordModel)
            else -> currentDayDinnerRecord.contains(recordModel)
        }

        if (isMealExist) {
            val e = Exception("This item has been added, check your list")
            emit(Response.Error(e))
            return@flow
        }
        repository.insertMealRecord(dateTime, mealTypeKey, recordModel).collect { res ->
            emit(res ?: Response.Error(null))
            if (res is Response.Success) {
                updateMealListByType(mealTypeKey, recordModel)
                updateGoalRecordUsecase.apply {
                    updateCaloriesInForCurrentDayRecord(
                        meal.calories,
                        meal.protein.getNutrientIndexFromString(),
                        meal.fat.getNutrientIndexFromString(),
                        meal.carbs.getNutrientIndexFromString()
                    )
                    updateCaloriesInForCurrentWeekRecord(
                        meal.calories,
                        meal.protein.getNutrientIndexFromString(),
                        meal.fat.getNutrientIndexFromString(),
                        meal.carbs.getNutrientIndexFromString()
                    )
                }
                dashboardUseCase.updateCaloPerDay(
                    CaloPerDay(
                        caloInt = meal.calories
                    )
                )
            }
        }
    }

    private fun getMealListByType(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ): List<MealRecordModel> {
        val result = mutableListOf<MealRecordModel>()
        CoroutineScope(ioDispatcher).launch {
            getMealsWithDayIdUsecase.getMealOfEachTypeInDayWithDayId(
                dayOfMonth, month, year, mealTypeKey
            ).collect { res ->
                if (res is Response.Success && res.data.isNotEmpty()) {
                    for (item in res.data) {
                        result.add(item)
                    }
                }
            }
        }
        return result
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
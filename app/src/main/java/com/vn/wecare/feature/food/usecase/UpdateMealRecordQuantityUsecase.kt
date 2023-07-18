package com.vn.wecare.feature.food.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.MealsRepository
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.home.goal.usecase.UpdateGoalRecordUsecase
import com.vn.wecare.feature.home.step_count.usecase.CaloPerDay
import com.vn.wecare.feature.home.step_count.usecase.DashboardUseCase
import com.vn.wecare.utils.getNutrientIndexFromString
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateMealRecordQuantityUsecase @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val updateGoalRecordUsecase: UpdateGoalRecordUsecase,
    private val dashboardUseCase: DashboardUseCase
) {

    init {
        dashboardUseCase.getCaloPerDay()
    }

    suspend fun plusMealRecord(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey, record: MealRecordModel
    ): Flow<Response<Boolean>?> {
        val quantity = record.quantity + 1

        updateGoalRecordUsecase.updateCaloriesInForCurrentDayRecord(
            record.calories,
            record.protein.getNutrientIndexFromString(),
            record.fat.getNutrientIndexFromString(),
            record.carbs.getNutrientIndexFromString()
        )
        updateGoalRecordUsecase.updateCaloriesInForCurrentWeekRecord(
            record.calories,
            record.protein.getNutrientIndexFromString(),
            record.fat.getNutrientIndexFromString(),
            record.carbs.getNutrientIndexFromString()
        )
        dashboardUseCase.updateCaloPerDay(
            CaloPerDay(caloInt = record.calories)
        )
        return mealsRepository.updateMealRecordQuantity(
            dayOfMonth, month, year, mealTypeKey, record.id, quantity
        )
    }

    suspend fun minusMealRecord(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey, record: MealRecordModel
    ): Flow<Response<Boolean>?> {
        val quantity = record.quantity - 1
        val result = if (quantity == 0) {
            mealsRepository.deleteMealRecord(dayOfMonth, month, year, mealTypeKey, record.id)
        } else mealsRepository.updateMealRecordQuantity(
            dayOfMonth, month, year, mealTypeKey, record.id, quantity
        )
        updateGoalRecordUsecase.updateCaloriesInForCurrentDayRecord(
            -record.calories,
            -record.protein.getNutrientIndexFromString(),
            -record.fat.getNutrientIndexFromString(),
            -record.carbs.getNutrientIndexFromString()
        )
        updateGoalRecordUsecase.updateCaloriesInForCurrentWeekRecord(
            -record.calories,
            -record.protein.getNutrientIndexFromString(),
            -record.fat.getNutrientIndexFromString(),
            -record.carbs.getNutrientIndexFromString()
        )
        dashboardUseCase.updateCaloPerDay(
            CaloPerDay(caloInt = -record.calories)
        )
        return result
    }
}
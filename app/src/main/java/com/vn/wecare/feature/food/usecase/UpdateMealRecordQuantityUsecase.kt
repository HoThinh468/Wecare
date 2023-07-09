package com.vn.wecare.feature.food.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.MealsRepository
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.home.goal.usecase.UpdateGoalRecordUsecase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateMealRecordQuantityUsecase @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val updateGoalRecordUsecase: UpdateGoalRecordUsecase
) {
    suspend fun plusMealRecord(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey, record: MealRecordModel
    ): Flow<Response<Boolean>?> {
        val quantity = record.quantity + 1
        updateGoalRecordUsecase.updateCaloriesInForCurrentDayRecord(record.calories)
        updateGoalRecordUsecase.updateCaloriesInForCurrentWeekRecord(record.calories)
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
        updateGoalRecordUsecase.updateCaloriesInForCurrentDayRecord(-record.calories)
        updateGoalRecordUsecase.updateCaloriesInForCurrentWeekRecord(-record.calories)
        return result
    }
}
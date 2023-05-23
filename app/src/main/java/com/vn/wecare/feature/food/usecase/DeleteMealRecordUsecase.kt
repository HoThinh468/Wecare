package com.vn.wecare.feature.food.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.MealsRepository
import com.vn.wecare.feature.food.data.model.MealTypeKey
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteMealRecordUsecase @Inject constructor(
    private val mealsRepository: MealsRepository
) {
    suspend fun deleteMealRecord(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey, mealId: Long
    ): Flow<Response<Boolean>?> =
        mealsRepository.deleteMealRecord(dayOfMonth, month, year, mealTypeKey, mealId)
}
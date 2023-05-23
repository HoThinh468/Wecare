package com.vn.wecare.feature.food.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.MealsRepository
import com.vn.wecare.feature.food.data.model.MealTypeKey
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateMealRecordUsecase @Inject constructor(
    private val mealsRepository: MealsRepository
) {
    suspend fun updateMealRecordQuantity(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey, mealId: Long, quantity: Int
    ): Flow<Response<Boolean>?> = mealsRepository.updateMealRecordQuantity(
        dayOfMonth, month, year, mealTypeKey, mealId, quantity
    )
}
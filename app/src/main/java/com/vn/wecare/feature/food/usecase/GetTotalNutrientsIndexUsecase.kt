package com.vn.wecare.feature.food.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.MealsRepository
import com.vn.wecare.feature.food.data.model.MealTypeKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetTotalNutrientsIndexUsecase @Inject constructor(
    private val mealsRepository: MealsRepository
) {
    private fun getTotalProteinOfEachDayOfEachType(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ): Flow<Response<Int>> = mealsRepository.getTotalProteinFromMealRecordInDayOfEachType(
        dayOfMonth, month, year, mealTypeKey
    )

    fun getTotalProteinOfEachDay(
        dayOfMonth: Int, month: Int, year: Int
    ) = combine(
        getTotalProteinOfEachDayOfEachType(dayOfMonth, month, year, MealTypeKey.BREAKFAST),
        getTotalProteinOfEachDayOfEachType(dayOfMonth, month, year, MealTypeKey.LUNCH),
        getTotalProteinOfEachDayOfEachType(dayOfMonth, month, year, MealTypeKey.SNACK),
        getTotalProteinOfEachDayOfEachType(dayOfMonth, month, year, MealTypeKey.DINNER)
    ) { breakfast, lunch, snack, dinner ->
        if (breakfast is Response.Success && lunch is Response.Success && snack is Response.Success && dinner is Response.Success) {
            val total = breakfast.data + lunch.data + snack.data + dinner.data
            Response.Success(total)
        } else Response.Error(null)
    }

    private fun getTotalFatOfEachDayOfEachType(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ): Flow<Response<Int>> = mealsRepository.getTotalFatFromMealRecordInDayOfEachType(
        dayOfMonth, month, year, mealTypeKey
    )

    fun getTotalFatOfEachDay(
        dayOfMonth: Int, month: Int, year: Int
    ) = combine(
        getTotalFatOfEachDayOfEachType(dayOfMonth, month, year, MealTypeKey.BREAKFAST),
        getTotalFatOfEachDayOfEachType(dayOfMonth, month, year, MealTypeKey.LUNCH),
        getTotalFatOfEachDayOfEachType(dayOfMonth, month, year, MealTypeKey.SNACK),
        getTotalFatOfEachDayOfEachType(dayOfMonth, month, year, MealTypeKey.DINNER)
    ) { breakfast, lunch, snack, dinner ->
        if (breakfast is Response.Success && lunch is Response.Success && snack is Response.Success && dinner is Response.Success) {
            val total = breakfast.data + lunch.data + snack.data + dinner.data
            Response.Success(total)
        } else Response.Error(null)
    }

    private fun getTotalCarbsOfEachDayOfEachType(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ): Flow<Response<Int>> = mealsRepository.getTotalCarbsFromMealRecordInDayOfEachType(
        dayOfMonth, month, year, mealTypeKey
    )

    fun getTotalCarbsOfEachDay(
        dayOfMonth: Int, month: Int, year: Int
    ) = combine(
        getTotalCarbsOfEachDayOfEachType(dayOfMonth, month, year, MealTypeKey.BREAKFAST),
        getTotalCarbsOfEachDayOfEachType(dayOfMonth, month, year, MealTypeKey.LUNCH),
        getTotalCarbsOfEachDayOfEachType(dayOfMonth, month, year, MealTypeKey.SNACK),
        getTotalCarbsOfEachDayOfEachType(dayOfMonth, month, year, MealTypeKey.DINNER)
    ) { breakfast, lunch, snack, dinner ->
        if (breakfast is Response.Success && lunch is Response.Success && snack is Response.Success && dinner is Response.Success) {
            val total = breakfast.data + lunch.data + snack.data + dinner.data
            Response.Success(total)
        } else Response.Error(null)
    }
}
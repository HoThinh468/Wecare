package com.vn.wecare.feature.food.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.repository.MealsRepository
import com.vn.wecare.feature.food.data.model.MealTypeKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetTotalInputCaloriesUsecase @Inject constructor(
    private val mealsRepository: MealsRepository
) {
    fun getTotalInputCaloriesOfEachDayForBreakfast(
        dayOfMonth: Int, month: Int, year: Int
    ): Flow<Response<Int>> = mealsRepository.getTotalCaloriesFromMealRecordInDayOfEachType(
        dayOfMonth, month, year, MealTypeKey.BREAKFAST
    )

    fun getTotalInputCaloriesOfEachDayForLunch(
        dayOfMonth: Int, month: Int, year: Int
    ): Flow<Response<Int>> = mealsRepository.getTotalCaloriesFromMealRecordInDayOfEachType(
        dayOfMonth, month, year, MealTypeKey.LUNCH
    )

    fun getTotalInputCaloriesOfEachDayForSnack(
        dayOfMonth: Int, month: Int, year: Int
    ): Flow<Response<Int>> = mealsRepository.getTotalCaloriesFromMealRecordInDayOfEachType(
        dayOfMonth, month, year, MealTypeKey.SNACK
    )

    fun getTotalInputCaloriesOfEachDayForDinner(
        dayOfMonth: Int, month: Int, year: Int
    ): Flow<Response<Int>> = mealsRepository.getTotalCaloriesFromMealRecordInDayOfEachType(
        dayOfMonth, month, year, MealTypeKey.DINNER
    )

    fun getTotalInputCaloriesOfEachDay(
        dayOfMonth: Int, month: Int, year: Int
    ) = combine(
        getTotalInputCaloriesOfEachDayForBreakfast(dayOfMonth, month, year),
        getTotalInputCaloriesOfEachDayForLunch(dayOfMonth, month, year),
        getTotalInputCaloriesOfEachDayForSnack(dayOfMonth, month, year),
        getTotalInputCaloriesOfEachDayForDinner(dayOfMonth, month, year)
    ) { breakfast, lunch, snack, dinner ->
        if (breakfast is Response.Success && lunch is Response.Success && snack is Response.Success && dinner is Response.Success) {
            val totalCalo = breakfast.data + lunch.data + snack.data + dinner.data
            Response.Success(totalCalo)
        } else Response.Error(null)
    }
}
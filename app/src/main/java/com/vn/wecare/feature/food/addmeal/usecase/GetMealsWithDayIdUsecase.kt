package com.vn.wecare.feature.food.addmeal.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.addmeal.data.MealsRepository
import com.vn.wecare.feature.food.addmeal.data.model.MealRecordModel
import com.vn.wecare.feature.food.addmeal.data.model.MealTypeKey
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject

class GetMealsWithDayIdUsecase @Inject constructor(
    private val mealsRepository: MealsRepository
) {
    suspend fun getMealOfEachTypeInDayWithDayId(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ): Flow<Response<List<MealRecordModel>?>> =
        mealsRepository.getMealOfEachTypeInDayWithDayId(dayOfMonth, month, year, mealTypeKey)

}
package com.vn.wecare.feature.food.data.datasource

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.model.MealByNutrients
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.feature.food.data.model.MealTypeKey
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface MealRecordDataSource {

    suspend fun insert(
        dateTime: Calendar, mealTypeKey: MealTypeKey, meal: MealRecordModel
    ): Flow<Response<Boolean>?>

    suspend fun delete(meal: MealByNutrients)

    suspend fun getMealWithId(dateTime: Calendar, id: Long): Flow<Response<MealByNutrients?>>

    suspend fun getAllMealsOfTypeInDayWithDayId(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ): Flow<Response<List<MealRecordModel>?>>
}
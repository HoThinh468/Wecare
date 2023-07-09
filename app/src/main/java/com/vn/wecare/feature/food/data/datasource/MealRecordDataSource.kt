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

    suspend fun delete(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey, mealId: Long
    ): Flow<Response<Boolean>?>

    suspend fun getMealWithId(dateTime: Calendar, id: Long): Flow<Response<MealByNutrients?>>

    suspend fun getAllMealsOfTypeInDayWithDayId(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ): Flow<Response<List<MealRecordModel>>>

    fun getTotalCaloriesFromMealRecordInDayOfEachType(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ) : Flow<Response<Int>>

    fun getTotalProteinFromMealRecordInDayOfEachType(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ) : Flow<Response<Int>>

    fun getTotalFatFromMealRecordInDayOfEachType(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ) : Flow<Response<Int>>

    fun getTotalCarbsFromMealRecordInDayOfEachType(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ) : Flow<Response<Int>>

    suspend fun updateQuantity(
        dayOfMonth: Int,
        month: Int,
        year: Int,
        mealTypeKey: MealTypeKey,
        mealId: Long,
        quantity: Int
    ): Flow<Response<Boolean>?>
}
package com.vn.wecare.feature.food.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.datasource.MealRecordDataSource
import com.vn.wecare.feature.food.data.datasource.MealsPagingSource
import com.vn.wecare.feature.food.data.datasource.NUMBER_OF_MEALS_EACH_LOAD
import com.vn.wecare.feature.food.data.di.RemoteMealsRecordDataSource
import com.vn.wecare.feature.food.data.model.MealByNutrients
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.data.model.toModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject

class MealsRepository @Inject constructor(
    private val mealsApiService: MealsApiService,
    @RemoteMealsRecordDataSource private val remoteDataSource: MealRecordDataSource
) {
    fun getMealsByNutrients(
        maxCalories: Int, minCalories: Int, maxProtein: Int, maxFat: Int, maxCarbs: Int
    ) = Pager(config = PagingConfig(
        pageSize = NUMBER_OF_MEALS_EACH_LOAD
    ), pagingSourceFactory = {
        MealsPagingSource(
            maxCalories, minCalories, maxProtein, maxFat, maxCarbs, mealsApiService
        )
    }).flow

    suspend fun insertMeal(
        dateTime: Calendar, mealTypeKey: MealTypeKey, meal: MealByNutrients
    ): Flow<Response<Boolean>?> = remoteDataSource.insert(dateTime, mealTypeKey, meal.toModel())

    suspend fun getMealOfEachTypeInDayWithDayId(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ): Flow<Response<List<MealRecordModel>?>> =
        remoteDataSource.getAllMealsOfTypeInDayWithDayId(dayOfMonth, month, year, mealTypeKey)

    suspend fun updateMealRecordQuantity(
        dayOfMonth: Int,
        month: Int,
        year: Int,
        mealTypeKey: MealTypeKey,
        mealId: Long,
        quantity: Int
    ): Flow<Response<Boolean>?> =
        remoteDataSource.updateQuantity(dayOfMonth, month, year, mealTypeKey, mealId, quantity)

    suspend fun deleteMealRecord(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey, mealId: Long
    ): Flow<Response<Boolean>?> =
        remoteDataSource.delete(dayOfMonth, month, year, mealTypeKey, mealId)
}
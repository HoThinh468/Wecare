package com.vn.wecare.feature.food.data.repository

import android.net.Uri
import android.util.Log
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.datasource.MealRecordDataSource
import com.vn.wecare.feature.food.data.datasource.YourOwnMealRemoteDataSource
import com.vn.wecare.feature.food.data.di.RemoteMealsRecordDataSource
import com.vn.wecare.feature.food.data.model.Meal
import com.vn.wecare.feature.food.data.model.MealNameSearchResult
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.data.service.MealsApiService
import com.vn.wecare.feature.food.search.ui.SearchFoodFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import javax.inject.Inject

class MealsRepository @Inject constructor(
    private val mealsApiService: MealsApiService,
    @RemoteMealsRecordDataSource private val remoteDataSource: MealRecordDataSource,
    private val yourOwnMealRemoteDataSource: YourOwnMealRemoteDataSource,
) {
    suspend fun insertMealRecord(
        dateTime: Calendar, mealTypeKey: MealTypeKey, meal: MealRecordModel
    ): Flow<Response<Boolean>?> = remoteDataSource.insert(dateTime, mealTypeKey, meal)

    suspend fun getMealOfEachTypeInDayWithDayId(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ): Flow<Response<List<MealRecordModel>>> =
        remoteDataSource.getAllMealsOfTypeInDayWithDayId(dayOfMonth, month, year, mealTypeKey)

    fun getTotalCaloriesFromMealRecordInDayOfEachType(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ): Flow<Response<Int>> = remoteDataSource.getTotalCaloriesFromMealRecordInDayOfEachType(
        dayOfMonth, month, year, mealTypeKey
    )

    fun getTotalProteinFromMealRecordInDayOfEachType(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ): Flow<Response<Int>> = remoteDataSource.getTotalProteinFromMealRecordInDayOfEachType(
        dayOfMonth, month, year, mealTypeKey
    )

    fun getTotalFatFromMealRecordInDayOfEachType(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ): Flow<Response<Int>> = remoteDataSource.getTotalFatFromMealRecordInDayOfEachType(
        dayOfMonth, month, year, mealTypeKey
    )

    fun getTotalCarbsFromMealRecordInDayOfEachType(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ): Flow<Response<Int>> = remoteDataSource.getTotalCarbsFromMealRecordInDayOfEachType(
        dayOfMonth, month, year, mealTypeKey
    )

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

    fun getMealByName(name: String): Flow<Response<MealNameSearchResult>> = flow {
        try {
            emit(
                Response.Success(
                    mealsApiService.getMealsByName(
                        name, 20, 50, 1000, 10, 100, 10, 100, 10, 100
                    )
                )
            )
        } catch (e: Exception) {
            emit(Response.Error(e))
            Log.d(SearchFoodFragment.searchMealTag, "Cannot get meal by name due to ${e.message}")
        }
    }

    fun insertYourOwnMealToFirebase(meal: Meal): Flow<Response<Boolean>?> =
        yourOwnMealRemoteDataSource.insertMealToFirebase(meal)

    fun insertMealImageToFirebaseStorage(
        timeStamp: Long, uri: Uri, mealKey: String
    ): Flow<Response<Boolean>?> = yourOwnMealRemoteDataSource.insertImage(timeStamp, uri, mealKey)

    fun deleteMealFromFirebase(meal: Meal): Flow<Response<Boolean>?> =
        yourOwnMealRemoteDataSource.deleteMealFromFirebase(meal)

    fun deleteMealImageFromFirebase(mealId: Long): Flow<Response<Boolean>?> =
        yourOwnMealRemoteDataSource.deleteMealImage(mealId)

    fun getYourOwnMealWithCategory(category: String): Flow<Response<List<Meal>>> =
        yourOwnMealRemoteDataSource.getYourOwnMealWithCategory(category)
}
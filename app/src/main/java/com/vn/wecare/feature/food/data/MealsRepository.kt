package com.vn.wecare.feature.food.data

import android.net.Uri
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.data.WecareDatabase
import com.vn.wecare.feature.food.data.datasource.MealRecordDataSource
import com.vn.wecare.feature.food.data.datasource.YourOwnMealRemoteDataSource
import com.vn.wecare.feature.food.data.di.RemoteMealsRecordDataSource
import com.vn.wecare.feature.food.data.model.Meal
import com.vn.wecare.feature.food.data.model.MealByNutrients
import com.vn.wecare.feature.food.data.model.MealNameSearchResult
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.data.model.toRecordModel
import com.vn.wecare.feature.food.search.ui.SearchFoodFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import javax.inject.Inject

class MealsRepository @Inject constructor(
    private val mealsApiService: MealsApiService,
    @RemoteMealsRecordDataSource private val remoteDataSource: MealRecordDataSource,
    private val yourOwnMealRemoteDataSource: YourOwnMealRemoteDataSource,
    private val wecareDatabase: WecareDatabase
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getMealsByNutrientsWithPagingSource(
        maxCalories: Int, minCalories: Int, maxProtein: Int, maxFat: Int, maxCarbs: Int
    ) = Pager(
        config = PagingConfig(
            pageSize = NUMBER_OF_MEALS_EACH_LOAD, initialLoadSize = NUMBER_OF_MEALS_EACH_LOAD
        ),
        pagingSourceFactory = {
            wecareDatabase.mealByNutrientsDao().pagingSource()
        },
        remoteMediator = MealByNutrientsRemoteMediator(
            wecareDatabase,
            mealsApiService,
            maxCalories,
            minCalories,
            maxProtein,
            maxFat,
            maxCarbs,
        ),
    ).flow

    suspend fun insertMeal(
        dateTime: Calendar, mealTypeKey: MealTypeKey, meal: MealByNutrients
    ): Flow<Response<Boolean>?> =
        remoteDataSource.insert(dateTime, mealTypeKey, meal.toRecordModel())

    suspend fun getMealOfEachTypeInDayWithDayId(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ): Flow<Response<List<MealRecordModel>>> =
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

    fun getMealByName(name: String): Flow<Response<MealNameSearchResult>> = flow {
        try {
            emit(
                Response.Success(
                    mealsApiService.getMealsByName(name, 50, 50, 1000, 10, 100, 10, 100, 10, 100)
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
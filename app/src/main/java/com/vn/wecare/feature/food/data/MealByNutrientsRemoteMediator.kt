package com.vn.wecare.feature.food.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vn.wecare.core.data.WecareDatabase
import com.vn.wecare.feature.food.data.model.MealByNutrientsEntity
import com.vn.wecare.feature.food.data.model.toEntity
import retrofit2.HttpException
import java.io.IOException

const val NUMBER_OF_MEALS_EACH_LOAD = 1

@OptIn(ExperimentalPagingApi::class)
class MealByNutrientsRemoteMediator(
    private val db: WecareDatabase,
    private val mealsApiService: MealsApiService,
    private val maxCalories: Int,
    private val minCalories: Int,
    private val maxProtein: Int,
    private val maxFat: Int,
    private val maxCarbs: Int,
) : RemoteMediator<Int, MealByNutrientsEntity>() {

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, MealByNutrientsEntity>
    ): MediatorResult {
        return try {
            val loadKey: Int = when (loadType) {
                LoadType.REFRESH -> 1

                LoadType.APPEND -> {
                    if (state.lastItemOrNull() == null) {
                        1
                    } else {
                        state.pages.size + 1
                    }
                }

                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }
            val meals = mealsApiService.getMealsByNutrients(
                maxCalories = maxCalories,
                minCalories = minCalories,
                maxProtein = maxProtein,
                maxFat = maxFat,
                maxCarbs = maxCarbs,
                number = NUMBER_OF_MEALS_EACH_LOAD,
                offset = loadKey * NUMBER_OF_MEALS_EACH_LOAD,
                random = true
            )

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.mealByNutrientsDao().clearAll()
                }
                val mealsEntity = meals.map { it.toEntity() }
                db.mealByNutrientsDao().insertAll(mealsEntity)
            }

            MediatorResult.Success(
                endOfPaginationReached = meals.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
package com.vn.wecare.feature.food.addmeal.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vn.wecare.feature.food.addmeal.data.MealsApiService
import com.vn.wecare.feature.food.addmeal.data.model.MealByNutrients

const val NUMBER_OF_MEALS_EACH_LOAD = 25

class MealsPagingSource(
    private val maxCalories: Int,
    private val minCalories: Int,
    private val maxProtein: Int,
    private val maxFat: Int,
    private val maxCarbs: Int,
    private val mealsApiService: MealsApiService
) : PagingSource<Int, MealByNutrients>() {
    override fun getRefreshKey(state: PagingState<Int, MealByNutrients>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MealByNutrients> {
        return try {
            val page = params.key ?: 1
            val result = mealsApiService.getMealsByNutrients(
                maxCalories = maxCalories,
                minCalories = minCalories,
                maxProtein = maxProtein,
                maxFat = maxFat,
                maxCarbs = maxCarbs,
                number = NUMBER_OF_MEALS_EACH_LOAD,
                offset = page * NUMBER_OF_MEALS_EACH_LOAD,
                random = true
            )

            LoadResult.Page(
                data = result,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (result.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
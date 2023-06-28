package com.vn.wecare.feature.food.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vn.wecare.feature.food.data.model.MealByNutrientsEntity

@Dao
interface MealByNutrientsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mealByNutrients: List<MealByNutrientsEntity>)

    @Query("SELECT * FROM meal_by_nutrients")
    fun pagingSource(): PagingSource<Int, MealByNutrientsEntity>

    @Query("DELETE FROM meal_by_nutrients")
    suspend fun clearAll()
}
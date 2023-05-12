package com.vn.wecare.feature.food.addmeal.data

import com.vn.wecare.feature.food.addmeal.data.model.MealByNutrients
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MealsApiService {
    @Headers(
        "X-RapidAPI-Key: 757895fb0amsh4a5cf9ad96f22d9p1789dajsn03fd559180c3",
        "X-RapidAPI-Host: spoonacular-recipe-food-nutrition-v1.p.rapidapi.com"
    )
    @GET("recipes/findByNutrients")
    suspend fun getMealsByNutrients(
        @Query("maxCalories") maxCalories: Int,
        @Query("minCalories") minCalories: Int,
        @Query("maxProtein") maxProtein: Int,
        @Query("maxProtein") maxFat: Int,
        @Query("maxProtein") maxCarbs: Int,
        @Query("number") number: Int,
        @Query("offset") offset: Int,
        @Query("random") random: Boolean
    ): List<MealByNutrients>
}
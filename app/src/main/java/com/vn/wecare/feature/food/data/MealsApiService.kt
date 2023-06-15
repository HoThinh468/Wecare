package com.vn.wecare.feature.food.data

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.model.MealByNutrients
import com.vn.wecare.feature.food.data.model.MealNameSearchResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val XRapidAPIKey = "a311ebaed1msh5036b9492cff379p198cebjsn40fe084441fc"
private const val XRapidAPIHost = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com"

interface MealsApiService {
    @Headers(
        "X-RapidAPI-Key: $XRapidAPIKey", "X-RapidAPI-Host: $XRapidAPIHost"
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

    @Headers(
        "X-RapidAPI-Key: $XRapidAPIKey", "X-RapidAPI-Host: $XRapidAPIHost"
    )
    @GET("recipes/complexSearch")
    suspend fun getMealsByName(
        @Query("query") query: String,
        @Query("number") number: Int,
        @Query("minCalories") minCalories: Int,
        @Query("maxCalories") maxCalories: Int,
        @Query("minProtein") minProtein: Int,
        @Query("maxProtein") maxProtein: Int,
        @Query("minCarbs") minCarbs: Int,
        @Query("maxCarbs") maxCarbs: Int,
        @Query("minFat") minFat: Int,
        @Query("maxFat") maxFat: Int,
    ): MealNameSearchResult
}
package com.vn.wecare.feature.food.data.service

import com.vn.wecare.feature.food.data.model.MealNameSearchResult
import com.vn.wecare.feature.food.data.model.mealplan.MealPlanResult
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val XRapidAPIKey = "a311ebaed1msh5036b9492cff379p198cebjsn40fe084441fc"
private const val XRapidAPIHost = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com"

interface MealsApiService {
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
        @Query("instructionsRequired") instructionsRequired: Boolean = true,
        @Query("addRecipeInformation") addRecipeInformation: Boolean = true
    ): MealNameSearchResult

    @Headers(
        "X-RapidAPI-Key: $XRapidAPIKey", "X-RapidAPI-Host: $XRapidAPIHost"
    )
    @GET("recipes/mealplans/generate")
    suspend fun getDailyMealPlanByTargetCalories(
        @Query("targetCalories") targetCalories: Int,
        @Query("timeFrame") timeFrame: String,
    ): MealPlanResult
}
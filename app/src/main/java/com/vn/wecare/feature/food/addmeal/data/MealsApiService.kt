package com.vn.wecare.feature.food.addmeal.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val GET_MEAL_BY_NUTRIENTS_BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(GET_MEAL_BY_NUTRIENTS_BASE_URL).build()

interface MealsApiService {
    @Headers(
        "X-RapidAPI-Key: 757895fb0amsh4a5cf9ad96f22d9p1789dajsn03fd559180c3",
        "X-RapidAPI-Host: spoonacular-recipe-food-nutrition-v1.p.rapidapi.com"
    )
    @GET("recipes/findByNutrients")
    suspend fun getMealsByNutrients(
        @Query("maxCalories") maxCalories: Int,
        @Query("minCalories") minCalories: Int,
        @Query("number") number: Int,
    ): List<MealsByNutrients>
}

object MealsApi {
    val getMealsByNutrients: MealsApiService by lazy { retrofit.create(MealsApiService::class.java) }
}
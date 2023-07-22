package com.vn.wecare.core.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vn.wecare.feature.food.data.service.MealsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val GET_MEAL_BY_NUTRIENTS_BASE_URL =
    "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Singleton
    @Provides
    fun provideMealsApiService(): MealsApiService =
        Retrofit.Builder().baseUrl(GET_MEAL_BY_NUTRIENTS_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
            .create(MealsApiService::class.java)
}
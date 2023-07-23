package com.vn.wecare.feature.food.data.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.model.mealplan.MealPlanResult
import com.vn.wecare.feature.food.data.service.MealsApiService
import com.vn.wecare.feature.food.mealplan.daily.DailyMealPlanFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MealPlanDataSource @Inject constructor(
    private val service: MealsApiService, private val db: FirebaseFirestore
) {

    fun getMealPlanWithTargetCalories(targetCalories: Int): Flow<Response<MealPlanResult>> = flow {
        try {
            val result = service.getDailyMealPlanByTargetCalories(
                targetCalories = targetCalories, timeFrame = "day"
            )
            Log.d(DailyMealPlanFragment.dailyMealPlanTag, "Meal plan result $result")
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e))
            Log.d(
                DailyMealPlanFragment.dailyMealPlanTag,
                "Cannot get meal plan with $targetCalories cal due to ${e.message}"
            )
        }
    }
}
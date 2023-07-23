package com.vn.wecare.feature.food.data.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.food.data.model.Recipe.RecipeInformation
import com.vn.wecare.feature.food.data.model.mealplan.MealPlanResult
import com.vn.wecare.feature.food.data.service.MealsApiService
import com.vn.wecare.feature.food.mealplan.daily.DailyMealPlanFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val MEAL_PLAN_COLLECTION_PATH = "mealPlan"
private const val DAY_COLLECTION_PATH = "days"
private const val MEAL_PLAN_DAILY_COLLECTION_PATH = "meals"

class MealPlanDataSource @Inject constructor(
    private val service: MealsApiService,
    private val db: FirebaseFirestore,
    private val accountService: AccountService
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

    fun getRecipeInformationWithId(id: Long): Flow<Response<RecipeInformation>> = flow {
        try {
            val result = service.getRecipeInformationWithId(id)
            Log.d(DailyMealPlanFragment.dailyMealPlanTag, "Meal plan result $result")
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e))
            Log.d(
                DailyMealPlanFragment.dailyMealPlanTag,
                "Cannot get recipe with id $id due to ${e.message}"
            )
        }
    }

    fun insertMealPlanToFirestore(
        dayId: String, recipeInformation: RecipeInformation
    ): Flow<Response<Boolean>> = flow {
        try {
            val result = db.collection(
                MEAL_PLAN_COLLECTION_PATH
            ).document(accountService.currentUserId).collection(DAY_COLLECTION_PATH).document(dayId)
                .collection(MEAL_PLAN_DAILY_COLLECTION_PATH)
                .document(recipeInformation.id.toString()).set(recipeInformation)
                .addOnSuccessListener {
                    Log.d(
                        DailyMealPlanFragment.dailyMealPlanTag, "Save recipe to remote successfully!"
                    )
                }.addOnFailureListener { e ->
                    Log.w(
                        DailyMealPlanFragment.dailyMealPlanTag, "Error writing document", e
                    )
                }.isSuccessful
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    fun getMealPlanFromFirestore(dayId: String): Flow<Response<List<RecipeInformation>?>> = flow {
        try {
            val result = db.collection(
                MEAL_PLAN_COLLECTION_PATH
            ).document(accountService.currentUserId).collection(DAY_COLLECTION_PATH).document(dayId)
                .collection(MEAL_PLAN_DAILY_COLLECTION_PATH).get().await()
            val listOfRecipes = result.documents.map {
                it.toObject(RecipeInformation::class.java) ?: RecipeInformation()
            }
            if (listOfRecipes.size < 3) {
                emit(Response.Success(null))
            } else {
                emit(Response.Success(listOfRecipes))
            }
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }
}
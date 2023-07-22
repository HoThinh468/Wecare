package com.vn.wecare.feature.food.data.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.addmeal.ui.AddMealFragment
import com.vn.wecare.feature.food.data.model.MealRecipe
import com.vn.wecare.feature.food.data.model.MealTypeKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val MEAL_COLLECTION_PATH = "mealsRecipe"

class MealRecipeDataSource @Inject constructor(
    private val db: FirebaseFirestore,
) {
    fun insertMealRecipeToFirestore(mealRecipe: MealRecipe) {
        try {
            db.collection(MEAL_COLLECTION_PATH).document(mealRecipe.id.toString()).set(mealRecipe)
                .addOnSuccessListener {
                    Log.d("Save meal with instructions", "Save successfully!")
                }.addOnFailureListener {
                    Log.d("Save meal with instructions", "Save fail!")
                }
        } catch (e: Exception) {
            Log.d("Save meal with instructions", "Save fail due to ${e.message}!")
        }
    }

    fun getMealRecipeWithRecipeId(recipeId: Long): Flow<Response<MealRecipe>> = flow {
        try {
            val result = db.collection(MEAL_COLLECTION_PATH).document(recipeId.toString()).get()
                .addOnSuccessListener {
                    Log.d(AddMealFragment.addMealTag, "Get meals successfully!")
                }.addOnFailureListener {
                    Log.d(AddMealFragment.addMealTag, "Get meals fail due to ${it.message}!")
                }.await()
            val recipe = result.toObject(MealRecipe::class.java)
            if (recipe == null) {
                emit(Response.Error(java.lang.Exception("No reciper exist with id $recipeId")))
                return@flow
            }
            emit(Response.Success(recipe))
        } catch (e: Exception) {
            Log.d(
                AddMealFragment.addMealTag,
                "Error! Cannot get meal recipe with id due to ${e.message}!"
            )
            emit(Response.Error(e))
        }
    }

    fun getMealRecipeWithMealTypeKey(mealTypeKey: MealTypeKey): Flow<Response<List<MealRecipe>>> =
        flow {
            try {
                val result = db.collection(MEAL_COLLECTION_PATH)
                    .whereArrayContains("dishTypes", mealTypeKey.value).orderBy(
                        "calories", Query.Direction.DESCENDING
                    ).limit(30).get().addOnSuccessListener {
                        Log.d(AddMealFragment.addMealTag, "Get meals successfully!")
                    }.addOnFailureListener {
                        Log.d(AddMealFragment.addMealTag, "Get meals fail due to ${it.message}!")
                    }.await()
                if (!result.isEmpty) {
                    val listOfMeal =
                        result.documents.map { it.toObject(MealRecipe::class.java) ?: MealRecipe() }
                            .toMutableList()
                    listOfMeal.removeIf { it == MealRecipe() }
                    emit(Response.Success(listOfMeal))
                } else {
                    emit(Response.Success(emptyList()))
                }
            } catch (e: Exception) {
                Log.d(
                    AddMealFragment.addMealTag,
                    "Error! Cannot get meals with recipe due to ${e.message}!"
                )
                emit(Response.Error(e))
            }
        }
}
package com.vn.wecare.feature.food.data.datasource

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.food.addmeal.ui.AddMealFragment
import com.vn.wecare.feature.food.data.model.Meal
import com.vn.wecare.feature.food.yourownmeal.addyourownmeal.ui.AddYourOwnMealFragment
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val YOUR_OWN_MEAL_COLLECTION_PATH = "yourOwnMeal"

class YourOwnMealRemoteDataSource @Inject constructor(
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val accountService: AccountService
) {
    fun insertMealToFirebase(meal: Meal): Flow<Response<Boolean>?> = flow {
        try {
            val result = getYourOwnMealDb(meal.category).document(meal.id.toString()).set(meal)
                .addOnSuccessListener {
                    Log.d(
                        AddYourOwnMealFragment.addMealTag,
                        "Add new custom meal to remote successfully!"
                    )
                }.addOnFailureListener {
                    Log.d(
                        AddYourOwnMealFragment.addMealTag,
                        "Add new custom meal to remote fail due to ${it.message}!"
                    )
                }.isSuccessful
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }.flowOn(ioDispatcher)

    fun insertImage(timeStamp: Long, imageUri: Uri, mealKey: String): Flow<Response<Boolean>?> =
        flow {
            try {
                val result = getStorageRef(timeStamp).putFile(imageUri).continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    getStorageRef(timeStamp).downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        updateMealWithImageUrl(timeStamp, mealKey, task.result.toString())
                    }
                }.isSuccessful
                emit(Response.Success(result))
            } catch (e: Exception) {
                Log.d(
                    AddYourOwnMealFragment.addMealTag,
                    "Upload new image with timestamp $timeStamp to remote fail due to ${e.message}!"
                )
                emit(Response.Error(e))
            }
        }.flowOn(ioDispatcher)

    fun getYourOwnMealWithCategory(category: String): Flow<Response<List<Meal>>> = flow {
        val recordList = arrayListOf<Meal>()
        try {
            val result = getYourOwnMealDb(category).get().addOnSuccessListener {
                Log.d(AddMealFragment.addMealTag, "Get your own meals from remote successfully!")
            }.addOnFailureListener { e ->
                Log.d(
                    AddMealFragment.addMealTag,
                    "Get your own meals from remote fail due to ${e.message}"
                )
            }.await()
            if (result != null) {
                for (i in result) {
                    recordList.add(i.toObject(Meal::class.java))
                }
            }
            emit(Response.Success(recordList))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    private fun updateMealWithImageUrl(timeStamp: Long, mealKey: String, imgUrl: String) {
        getYourOwnMealDb(mealKey).document(timeStamp.toString()).update("imgUrl", imgUrl)
            .addOnSuccessListener {
                Log.d(
                    AddYourOwnMealFragment.addMealTag, "Update meal image to remote successfully!"
                )
            }.addOnFailureListener {
                Log.d(
                    AddYourOwnMealFragment.addMealTag,
                    "Update meal image to remote fail due to ${it.message}!"
                )
            }
    }

    private fun getYourOwnMealDb(key: String): CollectionReference {
        return db.collection(YOUR_OWN_MEAL_COLLECTION_PATH).document(accountService.currentUserId)
            .collection(key)
    }

    private fun getStorageRef(timeStamp: Long): StorageReference =
        storage.reference.child("${accountService.currentUserId}/${timeStamp}.jpg")
}
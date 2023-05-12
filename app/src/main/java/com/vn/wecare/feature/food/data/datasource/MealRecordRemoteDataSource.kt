package com.vn.wecare.feature.food.data.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.food.data.model.MealByNutrients
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.addmeal.ui.AddMealFragment
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import javax.inject.Inject

private const val FOOD_COLLECTION_PATH = "food"

class MealRecordRemoteDataSource @Inject constructor(
    private val db: FirebaseFirestore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val accountService: AccountService
) : MealRecordDataSource {
    override suspend fun insert(
        dateTime: Calendar, mealTypeKey: MealTypeKey, meal: MealRecordModel
    ): Flow<Response<Boolean>?> = flow {
        try {
            val result = db.collection(FOOD_COLLECTION_PATH).document(accountService.currentUserId)
                .collection("${dateTime.get(Calendar.YEAR)}_${dateTime.get(Calendar.MONTH)}")
                .document("${dateTime.get(Calendar.DAY_OF_MONTH)}").collection(mealTypeKey.value)
                .document(meal.id.toString()).set(meal).addOnSuccessListener {
                    Log.d(AddMealFragment.addMealTag, "Insert food to remote successfully!")
                }.addOnFailureListener { e ->
                    Log.d(
                        AddMealFragment.addMealTag, "Insert food to remote fail due to ${e.message}"
                    )
                }.isSuccessful
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }.flowOn(ioDispatcher)

    override suspend fun delete(meal: MealByNutrients) {
        TODO("Not yet implemented")
    }

    override suspend fun getMealWithId(
        dateTime: Calendar, id: Long
    ): Flow<Response<MealByNutrients?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllMealsOfTypeInDayWithDayId(
        dayOfMonth: Int, month: Int, year: Int, mealTypeKey: MealTypeKey
    ): Flow<Response<List<MealRecordModel>?>> = flow {
        val recordList = arrayListOf<MealRecordModel>()
        try {
            val result = db.collection(FOOD_COLLECTION_PATH).document(accountService.currentUserId)
                .collection("${year}_$month")
                .document("$dayOfMonth").collection(mealTypeKey.value)
                .get().addOnSuccessListener {
                    Log.d(AddMealFragment.addMealTag, "Get meals from remote successfully!")
                }.addOnFailureListener { e ->
                    Log.d(
                        AddMealFragment.addMealTag, "Insert food to remote fail due to ${e.message}"
                    )
                }.await()
            if (result != null) {
                for (i in result) {
                    recordList.add(i.toObject(MealRecordModel::class.java))
                }
            }
            emit(Response.Success(recordList))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }.flowOn(ioDispatcher)


}
package com.vn.wecare.feature.home.step_count.data.datasource.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.home.step_count.data.datasource.StepsDatasource
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import com.vn.wecare.feature.home.step_count.data.model.StepsPerDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val WECARE_STEP_COUNT_COLLECTION_PATH = "stepCount"
private const val WECARE_STEPS_PER_DAY_DOCUMENT = "stepsPerDay"

class FirebaseStepsPerDayDataSource @Inject constructor(
    private val db: FirebaseFirestore, private val accountService: AccountService
) : StepsDatasource<StepsPerDay> {

    override suspend fun insert(input: StepsPerDay) {
        db.collection(WECARE_STEP_COUNT_COLLECTION_PATH).document(WECARE_STEPS_PER_DAY_DOCUMENT)
            .collection(accountService.currentUserId).document(input.dayId).set(input)
            .addOnSuccessListener {
                Log.d("Insert steps per day to firestore: ", input.toString())
            }.addOnFailureListener {
                Log.d("Cannot insert steps per day to firestore: ", it.toString())
            }
    }

    override suspend fun delete(inputs: List<StepsPerDay>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun getStepsPerDayWithHours(dayId: String): Flow<List<StepsPerHourEntity?>> {
        TODO("Not yet implemented")
    }

    override fun getStepsPerDay(dayId: String): Flow<StepsPerDay?> = flow {
        var stepsPerDay = StepsPerDay()
        db.collection(WECARE_STEP_COUNT_COLLECTION_PATH).document(accountService.currentUserId)
            .get().addOnSuccessListener {
                if (it != null) {
                    stepsPerDay = it.toObject(StepsPerDay::class.java) ?: StepsPerDay()
                }
            }.await()
        Log.d("Get steps per day from firestore: ", stepsPerDay.toString())
        emit(stepsPerDay)
    }

}
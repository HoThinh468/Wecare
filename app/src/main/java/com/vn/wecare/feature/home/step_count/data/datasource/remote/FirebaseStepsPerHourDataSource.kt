package com.vn.wecare.feature.home.step_count.data.datasource.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.feature.authentication.ui.service.AccountService
import com.vn.wecare.feature.home.step_count.data.datasource.StepsDatasource
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayWithHours
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val WECARE_STEPS_PER_HOUR_COLLECTION = "stepsPerHour"

class FirebaseStepsPerHourDataSource @Inject constructor(
    private val db: FirebaseFirestore, private val accountService: AccountService
) : StepsDatasource<StepsPerHour> {

    override suspend fun insert(input: StepsPerHour) {
        db.collection(WECARE_STEPS_PER_HOUR_COLLECTION).document(accountService.currentUserId)
            .collection(input.dayId).document(input.hourId).set(input).addOnSuccessListener {
                Log.d("Insert steps per hour to firestore: ", input.toString())
            }.addOnFailureListener {
                Log.d("Cannot insert steps per hour to firestore: ", it.toString())
            }
    }

    override suspend fun delete(inputs: List<StepsPerHour>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun getStepsPerDayWithHours(dayId: String): Flow<List<StepsPerDayWithHours?>> {
        TODO("Not yet implemented")
    }

    override fun getStepsPerDay(dayId: String): Flow<StepsPerHour?> {
        TODO("Not yet implemented")
    }

    fun getStepsPerHours(dayId: String): Flow<List<StepsPerHour?>> = flow {
        val stepsPerHours = mutableListOf<StepsPerHour>()
        db.collection(WECARE_STEPS_PER_HOUR_COLLECTION).document(accountService.currentUserId)
            .collection(dayId).get().addOnSuccessListener {
                if (it != null) {
                    for (stepPerHour in it) {
                        stepsPerHours.add(stepPerHour.toObject(StepsPerHour::class.java))
                    }
                }
            }.await()
        Log.d("Get steps per day from firestore: ", stepsPerHours.toString())
        emit(stepsPerHours)
    }
}
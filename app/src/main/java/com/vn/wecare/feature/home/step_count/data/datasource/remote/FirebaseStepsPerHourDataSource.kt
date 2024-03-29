package com.vn.wecare.feature.home.step_count.data.datasource.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.home.step_count.data.datasource.StepsDatasource
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import com.vn.wecare.feature.home.step_count.ui.view.StepCountFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val WECARE_STEPS_PER_HOUR_COLLECTION = "stepsPerHour"

class FirebaseStepsPerHourDataSource @Inject constructor(
    private val db: FirebaseFirestore, private val accountService: AccountService
) : StepsDatasource<StepsPerHour> {

    override suspend fun insert(input: StepsPerHour) {
        try {
            db.collection(WECARE_STEPS_PER_HOUR_COLLECTION).document(accountService.currentUserId)
                .collection(input.dayId).document(input.hourId).set(input).addOnSuccessListener {
                    Log.d(
                        StepCountFragment.stepCountTag,
                        "Insert steps per hour to firestore successfully with input: $input"
                    )
                }.addOnFailureListener {
                    Log.d(
                        StepCountFragment.stepCountTag,
                        "Insert steps per hour to firestore failed due to: ${it.message}"
                    )
                }
        } catch (e: FirebaseFirestoreException) {
            Log.d(
                StepCountFragment.stepCountTag,
                "Catch an exception while trying to insert steps per hour to firestore: ${e.message}"
            )
        }
    }

    override suspend fun delete(inputs: List<StepsPerHour>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun getStepsPerHourWithDayId(dayId: String): Flow<List<StepsPerHourEntity?>> {
        TODO("Not yet implemented")
    }

    override fun getStepsPerDay(dayId: String): Flow<StepsPerHour?> {
        TODO("Not yet implemented")
    }

    override fun getStepsPerHourWithHourId(hourId: String): Flow<StepsPerHourEntity?> {
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
        Log.d(StepCountFragment.stepCountTag, "Get steps per hour from firestore: $stepsPerHours")
        emit(stepsPerHours)
    }
}
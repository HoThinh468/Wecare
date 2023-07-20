package com.vn.wecare.feature.home.step_count.usecase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.ext.toDD_MM_yyyy
import com.vn.wecare.feature.home.step_count.data.model.StepsPerDay
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

const val PREVIOUS_TOTAL_SENSOR_STEPS = "previous_total_sensor_steps"

class GetStepsPerDayUsecase(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {
    fun getStepsInPreviousDay(currentSteps: StepsPerDay) : Flow<Response<StepsPerDay>> = flow {
        emit(
            try {
                val stepsPerDay = firestore
                    .collection("stepCount")
                    .document("stepsPerDay")
                    .collection(firebaseAuth.currentUser?.uid.toString())
                    .document(System.currentTimeMillis().minus(86400000L).toDD_MM_yyyy())
                    .get()
                    .await()
                    .toObject(StepsPerDay::class.java)

                if(stepsPerDay == null) {
                    firestore
                        .collection("stepCount")
                        .document("stepsPerDay")
                        .collection(firebaseAuth.currentUser?.uid.toString())
                        .document(System.currentTimeMillis().minus(86400000L).toDD_MM_yyyy())
                        .set(currentSteps)
                        .await()
                }

                Log.e("getStepsInPreviousDay:", " Success $stepsPerDay")

                Response.Success(stepsPerDay ?: StepsPerDay())
            } catch (e:Exception) {
                Log.e("getStepsInPreviousDay:", " Fail ${e.message}")
                Response.Error(e)
            }
        )
    }

    fun getListStepsHistory() : Flow<Response<List<StepsPerDay>>> = flow {
        emit(
            try {
                val listSteps = firestore
                    .collection("stepCount")
                    .document("stepsPerDay")
                    .collection(firebaseAuth.currentUser?.uid.toString())
                    .get()
                    .await()
                    .toObjects(StepsPerDay::class.java)

                Log.e("getListStepsHistory:", " Success $listSteps")

                Response.Success(listSteps)
            } catch (e:Exception) {
                Log.e("getListStepsHistory:", " Fail ${e.message}")
                Response.Error(e)
            }
        )
    }

    fun updateStepsPerDay(stepsPerDay: StepsPerDay) : Flow<Response<Boolean>> = flow {
        emit(
            try {
                firestore
                    .collection("stepCount")
                    .document("stepsPerDay")
                    .collection(firebaseAuth.currentUser?.uid.toString())
                    .document(System.currentTimeMillis().toDD_MM_yyyy())
                    .set(stepsPerDay)
                    .await()

                Log.e("updateStepsPerDay:", " Success $stepsPerDay")
                Response.Success(true)
            } catch (e:Exception) {
                Log.e("updateStepsPerDay:", "Fail ${e.message}")
                Response.Error(e)
            }
        )
    }

}
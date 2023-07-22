package com.vn.wecare.feature.home.step_count.usecase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.ext.toDD_MM_yyyy
import com.vn.wecare.feature.training.dashboard.history.model.TrainingHistory
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

data class CaloPerDay(
    val caloInt: Int = 0,
    val caloOut: Int = 0,
    val caloOutWalking: Int = 0,
    val caloOutTraining: Int = 0,
    val caloOutExercise: Int = 0,
)

private var previousCalo: CaloPerDay = CaloPerDay()

class DashboardUseCase(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {
    fun getCaloPerDay(): Flow<Response<CaloPerDay?>> = callbackFlow {

        val snapshotListener = firestore
            .collection("dashboard")
            .document(firebaseAuth.currentUser?.uid.toString())
            .collection("list")
            .document(System.currentTimeMillis().toDD_MM_yyyy())
            .addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val caloPerDay = snapshot.toObject(CaloPerDay::class.java)
                    Log.e("getCaloPerDay", " Success $caloPerDay")
                    previousCalo = caloPerDay ?: CaloPerDay()

                    Response.Success(caloPerDay)
                } else {
                    Log.e("response get training history", "Error: $e")
                    Response.Error(e)
                }
                trySend(response)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    suspend fun updateCaloPerDay(newCalo: CaloPerDay): Response<Boolean> {

        return try {
            val path = firestore
                .collection("dashboard")
                .document(firebaseAuth.currentUser?.uid.toString())
                .collection("list")
                .document(System.currentTimeMillis().toDD_MM_yyyy())
//            val previousCalo = path
//                .get()
//                .await()
//                .toObject(CaloPerDay::class.java)

            val caloInt = previousCalo?.caloInt?.plus(newCalo.caloInt) ?: newCalo.caloInt
            val caloOutExercise = previousCalo?.caloOutExercise?.plus(newCalo.caloOutExercise)
                ?: newCalo.caloOutExercise
            val caloOutWaking = if (newCalo.caloOutWalking == 0) {
                previousCalo.caloOutWalking
            } else {
                newCalo.caloOutWalking
            }
            val caloOutTraining = previousCalo?.caloOutTraining?.plus(newCalo.caloOutTraining)
                ?: newCalo.caloOutTraining

            val newValue = CaloPerDay(
                caloInt = caloInt,
                caloOutExercise = caloOutExercise,
                caloOutWalking = caloOutWaking,
                caloOutTraining = caloOutTraining,
                caloOut = caloOutExercise + caloOutWaking + caloOutTraining
            )

            path.set(newValue)

            Log.e("updateCaloPerDay", "Success $newValue")
            Response.Success(true)
        } catch (e: Exception) {
            Log.e("updateCaloPerDay", " Fail ${e.message}")
            Response.Error(e)
        }
    }

}
package com.vn.wecare.feature.home.step_count.usecase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.ext.toDD_MM_yyyy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

data class CaloPerDay(
    val caloInt: Int = 0,
    val caloOut: Int = 0,
    val caloOutWaking: Int = 0,
    val caloOutTraining: Int = 0,
    val caloOutExercise: Int = 0,
)

private var previousCalo: CaloPerDay = CaloPerDay()

class DashboardUseCase(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {
    fun getCaloPerDay(): Flow<Response<CaloPerDay>> = flow {
        emit(
            try {
                val caloPerDay = firestore
                    .collection("dashboard")
                    .document(firebaseAuth.currentUser?.uid.toString())
                    .collection("list")
                    .document(System.currentTimeMillis().toDD_MM_yyyy())
                    .get()
                    .await()
                    .toObject(CaloPerDay::class.java)

                Log.e("getCaloPerDay", " Success $caloPerDay")
                previousCalo = caloPerDay ?: CaloPerDay()

                Response.Success(caloPerDay ?: CaloPerDay())
            } catch (e: Exception) {
                Log.e("getCaloPerDay", " Fail ${e.message}")
                Response.Error(e)
            }
        )
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
            val caloOutWaking = if (newCalo.caloOutWaking == 0) {
                previousCalo.caloOutWaking
            } else {
                newCalo.caloOutWaking
            }
            val caloOutTraining = previousCalo?.caloOutTraining?.plus(newCalo.caloOutTraining)
                ?: newCalo.caloOutTraining

            val newValue = CaloPerDay(
                caloInt = caloInt,
                caloOutExercise = caloOutExercise,
                caloOutWaking = caloOutWaking,
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
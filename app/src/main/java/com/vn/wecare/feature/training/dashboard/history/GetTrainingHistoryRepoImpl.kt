package com.vn.wecare.feature.training.dashboard.history

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vn.wecare.feature.training.TrainingHistoryRepo
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.training.dashboard.history.model.TrainingHistory
import com.vn.wecare.feature.training.utils.formatCurrentMonth
import com.vn.wecare.feature.training.utils.formatNow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.collections.ArrayList

class TrainingHistoryRepoImpl @Inject constructor (
    private val trainingHistoryRef: CollectionReference
): TrainingHistoryRepo {

    override fun getTrainingHistoryFromFireStore() = callbackFlow {
        val snapshotListener = trainingHistoryRef
            .addSnapshotListener { snapshot, e ->
                val trainingHistoryResponse = if (snapshot != null) {
                    val histories = snapshot.toObjects(TrainingHistory::class.java)
                    Log.e("response get training history", "SUCCESS: $histories")
                    Response.Success(histories)
                } else {
                    Log.e("response get training history", "Error: $e")
                    Response.Error(e)
                }
                trySend(trainingHistoryResponse)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addTrainingHistory(history: TrainingHistory): Response<Boolean> {
        return try {
            trainingHistoryRef
                .document(formatNow())
                .set(history)
                .await()
            Log.e("response add training history", "Success")
            Response.Success(true)
        } catch (e: Exception) {
            Log.e("response add training history", "Error: $e")
            Response.Error(e)
        }
    }

    override fun getWeeklyCheck()= callbackFlow {
        val snapshotListener = Firebase.firestore
            .collection("training_history")
            .document(Firebase.auth.currentUser!!.uid)
            .collection(formatCurrentMonth())
            .addSnapshotListener { snapshot, e ->
                val trainingHistoryResponse = if (snapshot != null) {
                    val list: ArrayList<Int> = ArrayList()
                    for (doc in snapshot) {
                        doc.getLong("date")?.let {
                            list.add(it.toInt())
                        }
                    }
                    Response.Success(list)
                } else {
                    Log.e("response get training history", "Error: $e")
                    Response.Error(e)
                }
                trySend(trainingHistoryResponse)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addTrainedDate(): Response<Boolean> {
        return try {
            Firebase.firestore
                .collection("training_history")
                .document(Firebase.auth.currentUser!!.uid)
                .collection(formatCurrentMonth())
                .document(LocalDateTime.now().dayOfMonth.toString())
                .set(CustomDate(LocalDateTime.now().dayOfMonth))
                .await()
            Log.e("response add trained date", "Success")
            Response.Success(true)
        } catch (e: Exception) {
            Log.e("response add trained date", "Error: $e")
            Response.Error(e)
        }
    }
}

data class CustomDate(
    val date: Int = 0
)
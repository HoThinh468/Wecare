package com.vn.wecare.feature.goal

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.vn.wecare.feature.authentication.ui.service.AccountService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val WECARE_GOALS_COLLECTION_PATH = "goals"

class GoalsRepository @Inject constructor(
    private val db: FirebaseFirestore, private val accountService: AccountService
) {
    fun insertGoal(goals: Goals) {
        db.collection(WECARE_GOALS_COLLECTION_PATH).document(accountService.currentUserId)
            .set(goals).addOnSuccessListener {
                Log.d(
                    "Insert goals to firestore: ", "DocumentSnapshot successfully written!"
                )
            }.addOnFailureListener { e ->
                Log.w(
                    "Insert goals to firestore: ", "Error writing document", e
                )
            }
    }

    fun getGoalsWithUserId(userId: String): Flow<Goals> = flow {
        var goals = Goals()
        try {
            db.collection(WECARE_GOALS_COLLECTION_PATH).document(userId).get()
                .addOnSuccessListener {
                    if (it != null) {
                        goals = it.toObject(Goals::class.java) ?: Goals()
                    }
                }.await()
        } catch (e: FirebaseFirestoreException) {
            Log.d("Cannot get goals from firestore: ", e.message.toString())
        }
        Log.d("Get goals from firebase res: ", goals.toString())
        emit(goals)
    }
}
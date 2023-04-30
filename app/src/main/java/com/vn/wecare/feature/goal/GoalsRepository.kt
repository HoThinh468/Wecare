package com.vn.wecare.feature.goal

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.toObject
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.home.step_count.ui.view.StepCountFragment
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
        try {
            val res = db.collection(WECARE_GOALS_COLLECTION_PATH).document(userId).get().await()
            if (res != null && res.data != null) {
                emit(res.toObject(Goals::class.java) ?: Goals())
            } else emit(Goals())
        } catch (e: Exception) {
            Log.d(StepCountFragment.stepCountTag, "Error cannot get goals!")
        }
    }
}
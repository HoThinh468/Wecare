package com.vn.wecare.feature.goal

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.home.step_count.ui.view.StepCountFragment
import com.vn.wecare.feature.onboarding.OnboardingFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val WECARE_GOALS_COLLECTION_PATH = "goals"

class GoalsRepository @Inject constructor(
    private val db: FirebaseFirestore, private val accountService: AccountService
) {
    fun insertGoal(goal: Goal): Flow<Response<Boolean>> = flow {
        try {
            val result =
                db.collection(WECARE_GOALS_COLLECTION_PATH).document(accountService.currentUserId)
                    .set(goal).addOnSuccessListener {
                        Log.d(
                            OnboardingFragment.onboardingTag,
                            "DocumentSnapshot successfully written!"
                        )
                    }.addOnFailureListener { e ->
                        Log.w(
                            OnboardingFragment.onboardingTag, "Error writing document", e
                        )
                    }.isSuccessful
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    fun getGoalsWithUserId(userId: String): Flow<Goal> = flow {
        try {
            val res = db.collection(WECARE_GOALS_COLLECTION_PATH).document(userId).get().await()
            if (res != null && res.data != null) {
                emit(res.toObject(Goal::class.java) ?: Goal())
            } else emit(Goal())
        } catch (e: Exception) {
            emit(Goal())
            Log.d(StepCountFragment.stepCountTag, "Error cannot get goals!")
        }
    }
}
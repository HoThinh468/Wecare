package com.vn.wecare.feature.home.goal.data

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.home.goal.dashboard.GoalDashboardFragment
import com.vn.wecare.feature.home.goal.data.model.Goal
import com.vn.wecare.feature.home.goal.data.model.GoalDailyRecord
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.feature.home.goal.utils.generateGoalWeeklyRecordIdWithGoal
import com.vn.wecare.feature.home.goal.utils.getDayFromLongWithFormat
import com.vn.wecare.feature.home.step_count.ui.view.StepCountFragment
import com.vn.wecare.feature.onboarding.OnboardingFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val WECARE_GOALS_COLLECTION_PATH = "goals"
private const val WECARE_GOALS_LIST_COLLECTION_PATH = "goalsList"
private const val WECARE_GOALS_WEEKLY_RECORDS_COLLECTION_PATH = "weeklyRecords"
private const val WECARE_GOALS_DAILY_RECORDS_COLLECTION_PATH = "dailyRecords"

class GoalsRepository @Inject constructor(
    private val db: FirebaseFirestore, private val accountService: AccountService
) {
    fun insertGoal(goal: Goal): Flow<Response<Boolean>> = flow {
        try {
            val result = getGoalDocumentWithUserId().collection(
                WECARE_GOALS_LIST_COLLECTION_PATH
            ).document(goal.goalId).set(goal).addOnSuccessListener {
                Log.d(
                    OnboardingFragment.onboardingTag, "Save goal to remote successfully!"
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

    fun insertGoalWeeklyRecord(
        record: GoalWeeklyRecord, latestGoalId: String
    ): Flow<Response<Boolean>> = flow {
        try {
            val res = getGoalDocumentWithUserId().collection(WECARE_GOALS_LIST_COLLECTION_PATH)
                .document(latestGoalId).collection(WECARE_GOALS_WEEKLY_RECORDS_COLLECTION_PATH)
                .document(
                    generateGoalWeeklyRecordIdWithGoal(record)
                ).set(record).addOnSuccessListener {
                    Log.d("Goal track", "Save goal weekly record successfully!")
                }.addOnFailureListener {
                    Log.d("Goal track", "Save goal weekly record fail due to ${it.message}!")
                }.isSuccessful
            emit(Response.Success(res))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

//    fun getGoals(): Flow<Response<List<Goal>>> = flow {
//        try {
//            val res = getGoalDocumentWithUserId().collection(WECARE_GOALS_LIST_COLLECTION_PATH)
//                .orderBy("dateSetGoal", Query.Direction.DESCENDING).get().await()
//            if (!res.isEmpty) {
//                val listOfGoal = res.documents.map { it.toObject(Goal::class.java) ?: Goal() }
//                listOfGoal.toMutableList().removeIf { it == Goal() }
//                emit(Response.Success(listOfGoal))
//            } else {
//                emit(Response.Success(emptyList()))
//            }
//        } catch (e: Exception) {
//            Log.d(StepCountFragment.stepCountTag, "Error cannot get goals!")
//        }
//    }

    fun getDoneGoals(): Flow<Response<List<Goal>>> = flow {
        try {
            val res = getGoalDocumentWithUserId().collection(WECARE_GOALS_LIST_COLLECTION_PATH)
                .whereNotEqualTo("goalStatus", GoalStatus.INPROGRESS.value).get().await()
            if (!res.isEmpty) {
                val listOfGoal = res.documents.map { it.toObject(Goal::class.java) ?: Goal() }
                listOfGoal.toMutableList().removeIf { it == Goal() }
                emit(Response.Success(listOfGoal))
            } else {
                emit(Response.Success(emptyList()))
            }
        } catch (e: Exception) {
            Log.d(StepCountFragment.stepCountTag, "Error cannot get goals!")
        }
    }

    fun getLatestGoal(): Flow<Response<Goal>> = flow {
        try {
            val result = getGoalDocumentWithUserId().collection(
                WECARE_GOALS_LIST_COLLECTION_PATH
            ).whereLessThan("dateSetGoal", System.currentTimeMillis())
                .orderBy("dateSetGoal", Query.Direction.DESCENDING).limit(1).get().await()
            if (!result.isEmpty) {
                val goal = result.documents.first().toObject(Goal::class.java) ?: Goal()
                Log.d(
                    GoalDashboardFragment.goalDashboardTag,
                    "latest goal collect from firestore: $goal"
                )
                emit(Response.Success(goal))
            } else {
                emit(Response.Error(java.lang.Exception("Fail to get goals!")))
            }
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    fun getCurrentWeeklyRecord(goalId: String): Flow<Response<GoalWeeklyRecord>> = flow {
        try {
            val res = getGoalDocumentWithUserId().collection(WECARE_GOALS_LIST_COLLECTION_PATH)
                .document(goalId).collection(WECARE_GOALS_WEEKLY_RECORDS_COLLECTION_PATH)
                .whereLessThan("startDate", System.currentTimeMillis())
                .orderBy("startDate", Query.Direction.DESCENDING).limit(1).get().await()
            if (!res.isEmpty) {
                val week = res.documents.first().toObject(GoalWeeklyRecord::class.java)
                    ?: GoalWeeklyRecord()
                emit(Response.Success(week))
            } else {
                emit(Response.Error(java.lang.Exception("Fail to get latest weekly record!")))
            }
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    fun getOldWeeklyRecord(goalId: String): Flow<Response<List<GoalWeeklyRecord>>> = flow {
        try {
            val res = getGoalDocumentWithUserId().collection(WECARE_GOALS_LIST_COLLECTION_PATH)
                .document(goalId).collection(WECARE_GOALS_WEEKLY_RECORDS_COLLECTION_PATH)
                .whereLessThan("endDate", System.currentTimeMillis()).get().await()
            if (!res.isEmpty) {
                val listOfWeeklyGoal = res.documents.map {
                    it.toObject(GoalWeeklyRecord::class.java) ?: GoalWeeklyRecord()
                }
                listOfWeeklyGoal.toMutableList().removeIf { it == GoalWeeklyRecord() }
                emit(Response.Success(listOfWeeklyGoal))
            } else {
                emit(Response.Error(java.lang.Exception("Fail to get old weekly records")))
            }
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    fun updateInfoForGoalWeeklyRecord(
        field: String, value: Any, record: GoalWeeklyRecord
    ): Flow<Response<Boolean>> = flow {
        try {
            val res = getGoalDocumentWithUserId().collection(WECARE_GOALS_LIST_COLLECTION_PATH)
                .document(LatestGoalSingletonObject.getInStance().goalId)
                .collection(WECARE_GOALS_WEEKLY_RECORDS_COLLECTION_PATH).document(
                    generateGoalWeeklyRecordIdWithGoal(record)
                ).update(field, value).addOnSuccessListener {
                    Log.d("Goal track", "Update weekly record $field to $value successfully!")
                }.addOnFailureListener {
                    Log.d("Goal track", "Update weekly record record fail due to ${it.message}!")
                }.isSuccessful
            emit(Response.Success(res))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    fun updateInfoForCurrentGoalWeeklyRecord(
        field: String, value: Any
    ): Flow<Response<Boolean>> = flow {
        try {
            val res = getGoalDocumentWithUserId().collection(WECARE_GOALS_LIST_COLLECTION_PATH)
                .document(LatestGoalSingletonObject.getInStance().goalId)
                .collection(WECARE_GOALS_WEEKLY_RECORDS_COLLECTION_PATH).document(
                    generateGoalWeeklyRecordIdWithGoal(CurrentGoalWeeklyRecordSingletonObject.getInstance())
                ).update(field, value).addOnSuccessListener {
                    Log.d("Goal track", "Update weekly record $field to $value successfully!")
                }.addOnFailureListener {
                    Log.d("Goal track", "Update weekly record record fail due to ${it.message}!")
                }.isSuccessful
            emit(Response.Success(res))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    fun getAllWeeklyRecordsOfAGoal(goalId: String): Flow<Response<List<GoalWeeklyRecord>>> = flow {
        try {
            val res = getGoalDocumentWithUserId().collection(WECARE_GOALS_LIST_COLLECTION_PATH)
                .document(goalId).collection(WECARE_GOALS_WEEKLY_RECORDS_COLLECTION_PATH).get()
                .await()
            if (!res.isEmpty) {
                val listOfGoalWeeklyRecords = res.documents.map {
                    it.toObject(GoalWeeklyRecord::class.java) ?: GoalWeeklyRecord()
                }
                listOfGoalWeeklyRecords.toMutableList().removeIf { it == GoalWeeklyRecord() }
                emit(Response.Success(listOfGoalWeeklyRecords))
            } else {
                emit(Response.Success(emptyList()))
            }
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    fun insertGoalDailyRecord(record: GoalDailyRecord): Flow<Response<Boolean>> = flow {
        try {
            val res = getGoalDocumentWithUserId().collection(WECARE_GOALS_LIST_COLLECTION_PATH)
                .document(LatestGoalSingletonObject.getInStance().goalId)
                .collection(WECARE_GOALS_WEEKLY_RECORDS_COLLECTION_PATH).document(
                    generateGoalWeeklyRecordIdWithGoal(CurrentGoalWeeklyRecordSingletonObject.getInstance())
                ).collection(WECARE_GOALS_DAILY_RECORDS_COLLECTION_PATH).document(record.day)
                .set(record).addOnSuccessListener {
                    Log.d("Goal track", "Save goal daily record successfully!")
                }.addOnFailureListener {
                    Log.d("Goal track", "Save goal daily record fail due to ${it.message}!")
                }.isSuccessful
            emit(Response.Success(res))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    fun updateCaloriesAmountForGoalDailyRecord(
        field: String, value: Int, recordDay: String
    ): Flow<Response<Boolean>> = flow {
        try {
            val res = getGoalDocumentWithUserId().collection(WECARE_GOALS_LIST_COLLECTION_PATH)
                .document(LatestGoalSingletonObject.getInStance().goalId)
                .collection(WECARE_GOALS_WEEKLY_RECORDS_COLLECTION_PATH).document(
                    generateGoalWeeklyRecordIdWithGoal(CurrentGoalWeeklyRecordSingletonObject.getInstance())
                ).collection(WECARE_GOALS_DAILY_RECORDS_COLLECTION_PATH).document(recordDay)
                .update(field, value).addOnSuccessListener {
                    Log.d("Goal track", "Update goal $field to $value successfully!")
                }.addOnFailureListener {
                    Log.d("Goal track", "Update goal daily record fail due to ${it.message}!")
                }.isSuccessful
            emit(Response.Success(res))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    fun getCurrentGoalDailyReport(
        currentGoalId: String, currentWeekRecordId: String
    ): Flow<Response<GoalDailyRecord>> = flow {
        try {
            val res = getGoalDocumentWithUserId().collection(WECARE_GOALS_LIST_COLLECTION_PATH)
                .document(currentGoalId).collection(WECARE_GOALS_WEEKLY_RECORDS_COLLECTION_PATH)
                .document(currentWeekRecordId)
                .collection(WECARE_GOALS_DAILY_RECORDS_COLLECTION_PATH)
                .whereEqualTo("day", getDayFromLongWithFormat(System.currentTimeMillis())).limit(1)
                .get().await()
            if (!res.isEmpty) {
                val day =
                    res.documents.first().toObject(GoalDailyRecord::class.java) ?: GoalDailyRecord()
                emit(Response.Success(day))
            } else {
                emit(Response.Error(java.lang.Exception("No daily record available")))
            }
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    fun getAllGoalDailyRecordsInAWeek(
        goalId: String, weekId: String
    ): Flow<Response<List<GoalDailyRecord>>> = flow {
        try {
            val res = getGoalDocumentWithUserId().collection(WECARE_GOALS_LIST_COLLECTION_PATH)
                .document(goalId).collection(WECARE_GOALS_WEEKLY_RECORDS_COLLECTION_PATH)
                .document(weekId).collection(WECARE_GOALS_DAILY_RECORDS_COLLECTION_PATH).get()
                .await()
            if (!res.isEmpty) {
                val records = res.documents.map {
                    it.toObject(GoalDailyRecord::class.java) ?: GoalDailyRecord()
                }
                records.toMutableList().removeIf { it == GoalDailyRecord() }
                emit(Response.Success(records))
            } else {
                emit(Response.Error(java.lang.Exception("No daily record available")))
            }
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    fun updateGoalStatus(goalId: String, status: String): Flow<Response<Boolean>> = flow {
        try {
            val result = getGoalDocumentWithUserId().collection(
                WECARE_GOALS_LIST_COLLECTION_PATH
            ).document(goalId).update("goalStatus", status).addOnSuccessListener {
                Log.d(
                    OnboardingFragment.onboardingTag,
                    "Update goal with id $goalId to status $status successfully!"
                )
            }.addOnFailureListener { e ->
                Log.d(
                    OnboardingFragment.onboardingTag, "Error writing document", e
                )
            }.isSuccessful
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }

    private fun getGoalDocumentWithUserId(): DocumentReference {
        return db.collection(WECARE_GOALS_COLLECTION_PATH).document(accountService.currentUserId)
    }
}
package com.vn.wecare.feature.exercises.services

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.gson.reflect.TypeToken
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.model.ExerciseType
import com.vn.wecare.core.model.ListDone
import com.vn.wecare.core.model.ListReviewsItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private var reviewListSize: Int = 0
private var listDone: ListDone? = null

class ExerciseServicesImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : ExerciseServices {

    override suspend fun getReviewsList(type: ExerciseType, index: Int) = callbackFlow {
        val snapshotListener =
            firestore
                .collection("exercise")
                .document("reviews")
                .collection(type.toString().lowercase())
                .document(index.toString())
                .collection("users_reviews")
                .addSnapshotListener { snapshot, e ->
                    val reviewListResponse = if (snapshot != null) {
                        val list = snapshot.toObjects(ListReviewsItem::class.java)
                        reviewListSize = list.size
                        Log.d("get list review success", list.toString())
                        Response.Success(list)
                    } else {
                        Log.d("get list review error", e.toString())
                        Response.Error(e)
                    }
                    trySend(reviewListResponse)
                }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun likeReview(
        type: ExerciseType,
        index: Int,
        indexReview: Int,
        newLikeCount: Int
    ): Response<Boolean> {
        return try {
            firestore
                .collection("exercise")
                .document("reviews")
                .collection(type.toString().lowercase())
                .document(index.toString())
                .collection("users_reviews")
                .document(indexReview.toString())
                .update("likeCount", newLikeCount)
                .await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    override suspend fun getReviewLikeCount(
        type: ExerciseType,
        index: Int,
        indexReview: Int
    ): Flow<Response<Int>> = flow {
        val data = firestore
            .collection("exercise")
            .document("reviews")
            .collection(type.toString().lowercase())
            .document(index.toString())
            .collection("users_reviews")
            .document(indexReview.toString())
            .get()
            .await()
            .toObject<ListReviewsItem>()

        if (data != null) {
            emit(Response.Success(data.likeCount))
            Log.d("get review like count success", data.likeCount.toString())
        } else {
            emit(Response.Error(Exception("Document does not exist")))
            Log.d("get review like count error", "Error")

        }
    }

    override suspend fun updateListLikeAccount(
        type: ExerciseType,
        index: Int,
        indexReview: Int
    ): Response<Boolean> = try {
        firestore
            .collection("exercise")
            .document("reviews")
            .collection(type.toString().lowercase())
            .document(index.toString())
            .collection("users_reviews")
            .document(indexReview.toString())
            .update("listLikeAccount", FieldValue.arrayUnion(auth.currentUser?.uid.toString()))
            .await()

        Log.d("update review like count success", "SUCCESS")
        Response.Success(true)
    } catch (e: Exception) {
        Response.Error(e)
    }

    override suspend fun getListDoneFullBody(): Flow<Response<ListDone?>?> = flow {
        emit(
            try {
                val list = firestore
                    .collection("exercise")
                    .document("check_full_body")
                    .collection("is_done")
                    .document(auth.currentUser!!.uid)
                    .get()
                    .await()
                    .toObject(ListDone::class.java)

                listDone = list

                Log.d("get list done ", "SUCCESS $list")
                Response.Success(list)
            } catch (e: Exception) {
                Log.d("get list done ", "Error")
                Response.Error(e)
            }
        )
    }

    override suspend fun addNewReview(
        type: ExerciseType,
        index: Int,
        content: String,
        rate: Int
    ): Response<Boolean> = try {
        if (rate != 0) {
            firestore
                .collection("exercise")
                .document("reviews")
                .collection(type.toString().lowercase())
                .document(index.toString())
                .collection("users_reviews")
                .document(reviewListSize.toString())
                .set(
                    ListReviewsItem(
                        userName = if (auth.currentUser?.displayName.isNullOrBlank())
                            "Unknown user"
                        else auth.currentUser?.displayName ?: "",
                        content = content,
                        rate = rate,
                        time = System.currentTimeMillis(),
                        userId = auth.currentUser?.uid ?: ""
                    )
                )
                .await()
        }
        Response.Success(true)
    } catch (e: Exception) {
        Response.Error(e)
    }

    override suspend fun updateListDoneFullBody(
        dateIndex: Int
    ): Response<Boolean> = try {
        if (listDone?.listDone == null) {
            firestore.collection("exercise")
                .document("check_full_body")
                .collection("is_done")
                .document(auth.currentUser!!.uid)
                .set(
                    ListDone(
                        listDone = listOf(0)
                    )
                )

            Log.d("update list done ", "SUCCESS == 0")
        } else {
            firestore
                .collection("exercise")
                .document("check_full_body")
                .collection("is_done")
                .document(auth.currentUser!!.uid)
                .update("listDone", FieldValue.arrayUnion(dateIndex))

            Log.d("update list done ", "SUCCESS != 0")
        }

        Response.Success(true)
    } catch (e: Exception) {
        Log.d("update list done ", "Fail: ${e.message}")
        Response.Error(e)
    }
}
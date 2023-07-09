package com.vn.wecare.feature.home.bmi.service

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.core.WecareUserSingletonObject
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.bmi.model.BMIHistoryEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class BMIServicesImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : BMIServices {
    override suspend fun addBMIHistory(age: Int, gender: Boolean, height: Int?, weight: Int?) {
        try {

            val docRef = firestore
                .collection("BMIHistory")
                .document(auth.currentUser?.uid.toString())

            docRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    val existingArray = documentSnapshot.get("listHistory") as? ArrayList<*>
                    val updatedArray = ArrayList<Any?>()

                    existingArray?.let {
                        updatedArray.addAll(it)
                    }

                    val user = WecareUserSingletonObject.getInstance()
                    updatedArray.add(
                        BMIHistoryEntity(
                            height = height ?: user.height ?: 180,
                            weight = weight ?: user.weight ?: 80,
                            gender = gender,
                            age = age,
                            time = System.currentTimeMillis()
                        )
                    )
                    docRef.update("listHistory", updatedArray)
                        .addOnSuccessListener {
                            println("Array updated successfully!")
                        }
                        .addOnFailureListener { exception ->
                            println("Error updating array: $exception")
                        }
                }
        } catch (e: Exception) {
            Log.e("BMIServices", "addBMIHistory: ${e.message} ")
            return
        }
    }

    override suspend fun fetchListHistory(): Flow<Response<List<BMIHistoryEntity>>> = flow {
        emit(
            try {
                val documentSnapshot = firestore
                    .collection("BMIHistory")
                    .document(auth.currentUser?.uid.toString())
                    .get()
                    .await()
                if (documentSnapshot.exists()) {
                    val bmiHistoryList = mutableListOf<BMIHistoryEntity>()

                    val dataArray = documentSnapshot.get("listHistory") as? ArrayList<*>
                    dataArray?.forEach { item ->
                        if (item is HashMap<*, *>) {
                            val height = item["height"] as Long
                            val weight = item["weight"] as Long
                            val age = item["age"] as Long
                            val gender = item["gender"] as Boolean
                            val time = item["time"] as Long

                            val bmiHistory =
                                BMIHistoryEntity(
                                    height.toInt(),
                                    weight.toInt(),
                                    age.toInt(),
                                    gender,
                                    time
                                )
                            bmiHistoryList.add(bmiHistory)
                        }
                    }
                    Response.Success(bmiHistoryList)
                } else {
                    Response.Error(Exception("BMIHistory not found"))
                }
            } catch (e: Exception) {
                Log.e("fetchListHistory", "Error: ${e.message}")
                Response.Error(e)
            }
        )
    }

    override suspend fun updateListHistory(updatedList: List<BMIHistoryEntity>): Flow<Response<Boolean>> =
        flow {
            emit(
                try {
                    val docRef = firestore
                        .collection("BMIHistory")
                        .document(auth.currentUser?.uid.toString())

                    docRef.update("listHistory", updatedList)
                        .addOnSuccessListener {
                            println("Array updated successfully!")
                        }
                        .addOnFailureListener { exception ->
                            println("Error updating array: $exception")
                        }.await()

                    Response.Success(true)
                } catch (e: Exception) {
                    Log.e("updateListHistory", "Error: ${e.message}")
                    Response.Error(e)
                }
            )
        }
}
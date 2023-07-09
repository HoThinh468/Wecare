package com.vn.wecare.feature.home.bmi.service

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.bmi.model.BMIHistoryEntity
import kotlinx.coroutines.flow.Flow

interface BMIServices {
    suspend fun addBMIHistory(age: Int, gender: Boolean, height: Int?, weight: Int?)
    suspend fun fetchListHistory(): Flow<Response<List<BMIHistoryEntity>>>
    suspend fun updateListHistory(updatedList: List<BMIHistoryEntity>): Flow<Response<Boolean>>
}
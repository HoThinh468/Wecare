package com.vn.wecare.feature.training

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.training.dashboard.history.model.TrainingHistory
import kotlinx.coroutines.flow.Flow

interface TrainingHistoryRepo {
    fun getTrainingHistoryFromFireStore(): Flow<Response<List<TrainingHistory>>>
    suspend fun addTrainingHistory(history: TrainingHistory): Response<Boolean>
    fun getWeeklyCheck() : Flow<Response<List<Int>>>
    suspend fun addTrainedDate(): Response<Boolean>
}
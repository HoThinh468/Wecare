package com.vn.wecare.feature.training.dashboard

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.feature.training.dashboard.history.model.Response
import com.vn.wecare.feature.training.dashboard.history.model.TrainingHistory
import com.vn.wecare.feature.training.dashboard.usecase.GetTrainingHistory
import com.vn.wecare.feature.training.dashboard.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {
    var trainingHistoryResponse by mutableStateOf<Response<List<TrainingHistory>>>(Response.Loading)
        private set

    var weeklyCheckResponse by mutableStateOf<Response<List<Int>>>(Response.Loading)
        private set

    var totalKcal by mutableStateOf(0.0)
        private set

    var totalDuration by mutableStateOf(0)
        private set

    init {
        getListTrainingHistory()
        getWeeklyCheck()
    }

    private fun getListTrainingHistory() = viewModelScope.launch {
        useCases.getTrainingHistory().collect { response ->
            Log.e("Training viewModel get List Training History", response.toString())
            trainingHistoryResponse = response
        }
    }

    private fun getWeeklyCheck() = viewModelScope.launch {
        useCases.getWeeklyCheck().collect { response ->
            Log.e("Training viewModel get List Training Date in Week", response.toString())
            weeklyCheckResponse = response
        }
    }

    fun calculateTotalCalo(list: List<TrainingHistory>) {
        totalKcal = 0.0
        for(i in list) {
            totalKcal += i.kcal
        }
    }

    fun calculateTotalDuration(list: List<TrainingHistory>) {
        totalDuration = 0
        for(i in list) {
            totalDuration += i.duration
        }
    }
}
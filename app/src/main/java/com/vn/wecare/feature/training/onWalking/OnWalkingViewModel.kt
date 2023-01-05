package com.vn.wecare.feature.training.onWalking

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.feature.training.dashboard.history.model.Response
import com.vn.wecare.feature.training.dashboard.history.model.TrainingHistory
import com.vn.wecare.feature.training.dashboard.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnWalkingViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    var addTrainingHistoryResponse by mutableStateOf<Response<Boolean>>(Response.Loading)
        private set

    var addTrainedDateResponse by mutableStateOf<Response<Boolean>>(Response.Loading)
        private set

    private var _distance = mutableStateOf(0.0)
    val distance: State<Double>
        get() = _distance

    private var _kcal = mutableStateOf(0.0)
    val kcal: State<Double>
        get() = _kcal

    fun addTrainingHistory(history: TrainingHistory) = viewModelScope.launch {
        addTrainingHistoryResponse = Response.Loading
        useCases.addTrainingHistory(history)
    }

    fun addTrainedDate() = viewModelScope.launch {
        addTrainedDateResponse = Response.Loading
        useCases.addTrainedDate()
    }

    fun updateNewDistance(newDistance: Double) {
        _distance.value = newDistance
        updateKcal(newDistance)
    }

    private fun updateKcal(distance: Double) {
        _kcal.value = distance*62.5
    }

    fun reset() {
        _kcal.value = 0.0
        _distance.value = 0.0
    }
}
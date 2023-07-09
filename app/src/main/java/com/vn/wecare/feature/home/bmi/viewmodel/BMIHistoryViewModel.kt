package com.vn.wecare.feature.home.bmi.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.model.ListReviewsItem
import com.vn.wecare.feature.home.bmi.model.BMIHistoryEntity
import com.vn.wecare.feature.home.bmi.usecase.BMIUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class BMIHistoryViewModel @Inject constructor(
    private val bmiUseCase: BMIUseCase
) : ViewModel() {

    init {
        fetchListHistory()
    }

    var listHistoryResponse by mutableStateOf<Response<List<BMIHistoryEntity>>>(Response.Loading)
        private set
    private var _listHistory = MutableStateFlow<List<BMIHistoryEntity>>(emptyList())
    val listHistory: StateFlow<List<BMIHistoryEntity>>
        get() = _listHistory

    var response by mutableStateOf<Response<Boolean>>(Response.Loading)
        private set
    private var _flow = MutableStateFlow<Boolean>(false)
    val flow: StateFlow<Boolean>
        get() = _flow

    private fun fetchListHistory() = viewModelScope.launch {
        bmiUseCase.fetchListHistory.invoke().collect { response ->
            listHistoryResponse = response
            if (listHistoryResponse is Response.Success) {
                _listHistory.emit(
                    (listHistoryResponse as Response.Success<List<BMIHistoryEntity>>).data.reversed()
                )
            }
        }
    }

    fun removeBMIHistory(itemIndex: Int) = viewModelScope.launch {
        setupFlow()


        val list = _listHistory.value.toMutableList()
        list.removeAt(itemIndex)
        _listHistory.emit(list.toList())
    }

    fun updateBMIHistory(itemIndex: Int, item: BMIHistoryEntity) = viewModelScope.launch {
        setupFlow()

        val list = _listHistory.value.toMutableList()
        list[itemIndex] = item
        _listHistory.emit(list.toList())
    }

    private fun setupFlow() = viewModelScope.launch {
        bmiUseCase.updateBMIHistory.invoke(_listHistory.value).collect {
            response = it
            if(response is Response.Success) {
                _flow.emit(true)
            }
        }
    }
}
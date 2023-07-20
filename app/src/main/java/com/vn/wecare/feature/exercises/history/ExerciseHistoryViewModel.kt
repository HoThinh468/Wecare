package com.vn.wecare.feature.exercises.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.model.ExerciseType
import com.vn.wecare.core.model.HistoryItem
import com.vn.wecare.feature.exercises.usecase.Usecases
import com.vn.wecare.feature.home.step_count.usecase.CaloPerDay
import com.vn.wecare.feature.home.step_count.usecase.DashboardUseCase
import com.vn.wecare.utils.getFirstWeekdayTimestamp
import com.vn.wecare.utils.getLastWeekdayTimestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

const val oneWeekInMillis = 604800000L
const val oneDayInMillis = 86400000L

@HiltViewModel
class ExerciseHistoryViewModel @Inject constructor(
    private val usecases: Usecases,
    private val dashboardUseCase: DashboardUseCase
) : ViewModel() {

    init {
        loadListHistory()
        dashboardUseCase.getCaloPerDay()
    }


    private var _historyViewTime = MutableStateFlow(System.currentTimeMillis())
    val historyViewTime: StateFlow<Long>
        get() = _historyViewTime

    var listHistoryResponse by mutableStateOf<Response<List<HistoryItem>>>(Response.Loading)
    private val _listHistory = MutableStateFlow<List<HistoryItem>?>(null)
    val listHistory: StateFlow<List<HistoryItem>?>
        get() = _listHistory

    private val _listHistoryDisplay = MutableStateFlow<List<HistoryItem>?>(null)
    val listHistoryDisplay: StateFlow<List<HistoryItem>?>
        get() = _listHistoryDisplay

    private val _listChartDisplay = MutableStateFlow<List<Float>>(listOf(0f))
    val listChartDisplay: StateFlow<List<Float>>
        get() = _listChartDisplay

    private val _isNextBtnEnable = MutableStateFlow<Boolean>(false)
    val isNextBtnEnable: StateFlow<Boolean>
        get() = _isNextBtnEnable

    fun increaseViewTime() {
        _historyViewTime.value += oneWeekInMillis
        filterListHistory()
        checkIsNextBtnEnable()
    }

    fun decreaseViewTime() {
        _historyViewTime.value -= oneWeekInMillis
        filterListHistory()
        checkIsNextBtnEnable()
    }

    private fun loadListHistory() = viewModelScope.launch {
        usecases.getListExerciseHistory.invoke().collect { response ->
            listHistoryResponse = response
            if (listHistoryResponse is Response.Success) {
                _listHistory.value =
                    (listHistoryResponse as Response.Success<List<HistoryItem>>).data
                filterListHistory()
                checkIsNextBtnEnable()
            }
        }
    }

    fun addNewHistory(type: ExerciseType, kcal: Float, duration: Int) = viewModelScope.launch {
        usecases.addNewExerciseHistory(type, kcal, duration)

        dashboardUseCase.updateCaloPerDay(CaloPerDay(caloOutExercise = kcal.toInt()))
    }

    private fun filterListHistory() {
        val list = _listHistory.value ?: return
        val listDisplay = mutableListOf<HistoryItem>()
        val listChart = mutableListOf(0f, 0f, 0f, 0f, 0f, 0f, 0f)
        for (i in list) {
            if (i.time >= getFirstWeekdayTimestamp(_historyViewTime.value)
                && i.time < getFirstWeekdayTimestamp(_historyViewTime.value) + oneWeekInMillis
            ) {
                listDisplay.add(i)
                for (j in 0..6) {
                    if (i.time >= getFirstWeekdayTimestamp(_historyViewTime.value) + j * oneDayInMillis
                        && i.time < getFirstWeekdayTimestamp(_historyViewTime.value) + (j + 1) * oneDayInMillis
                    ) {
                        listChart[j] = listChart[j] + i.kcal
                    }
                }
            }
        }
        _listChartDisplay.value = listChart
        _listHistoryDisplay.value = listDisplay
    }

    private fun checkIsNextBtnEnable() {
        _isNextBtnEnable.value =
            _historyViewTime.value + oneWeekInMillis < getLastWeekdayTimestamp(System.currentTimeMillis())
    }

}
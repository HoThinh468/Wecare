package com.vn.wecare.feature.home.water.report

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.water.data.WaterRecordRepository
import com.vn.wecare.feature.home.water.data.model.WaterRecordEntity
import com.vn.wecare.feature.home.water.tracker.DEFAULT_DRANK_AMOUNT
import com.vn.wecare.feature.home.water.tracker.DEFAULT_WATER_TARGET_AMOUNT
import com.vn.wecare.utils.getDayFormatWithOnlyMonthPrefix
import com.vn.wecare.utils.getDayFormatWithYear
import com.vn.wecare.utils.getDayOfWeekPrefix
import com.vn.wecare.utils.getFirstDayOfWeekWithGivenDate
import com.vn.wecare.utils.getLastDayOfWeekWithGivenDay
import com.vn.wecare.utils.getListOfDayWithStartAndEndDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject

data class WaterDayReport(
    val targetAmount: Int, val drankAmount: Int, val dayOfWeek: String
)

data class WaterReportUiState(
    val isLoadingData: Response<Boolean>? = null,
    val averageAmount: Int = 0,
    val caloriesBurnt: Float = 0f,
    val averageLevel: Int = 0,
    val firstDayOfWeek: String = "",
    val lastDayOfWeek: String = "",
    val dayReportList: ArrayList<WaterDayReport> = arrayListOf()
)

@SuppressLint("SimpleDateFormat")
@HiltViewModel
class WaterReportViewModel @Inject constructor(
    private val waterRecordRepository: WaterRecordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WaterReportUiState())
    val uiState = _uiState.asStateFlow()
    private var desiredViewDay = mutableStateOf(LocalDate.now())

    fun initReportView() {
        updateFirstAndLastDayOfWeekWithGivenDay(LocalDate.now())
        fetchWaterDayRecordDataFromFirebase()
    }

    fun onPreviousWeekClick() {
        desiredViewDay.value = desiredViewDay.value.minusDays(7)
        updateFirstAndLastDayOfWeekWithGivenDay(desiredViewDay.value)
    }

    fun onNextWeekClick() {
        desiredViewDay.value = desiredViewDay.value.plusDays(7)
        updateFirstAndLastDayOfWeekWithGivenDay(desiredViewDay.value)
    }

    private fun fetchWaterDayRecordDataFromFirebase() {
        _uiState.update { it.copy(isLoadingData = Response.Loading) }
        val listOfDay = getListOfDayWithStartAndEndDay(
            getFirstDayOfWeekWithGivenDate(desiredViewDay.value),
            getLastDayOfWeekWithGivenDay(desiredViewDay.value)
        )
        val listOfListWaterRecord = arrayListOf<List<WaterRecordEntity>>()
        viewModelScope.launch {
            repeat(listOfDay.size) { i ->
                val day = listOfDay[i]
                // monthValue have to minus 1 to get the right monthValue equals with the one get from CalendarUtil
                waterRecordRepository.getRecordWithDayIdFromRemote(
                    day.dayOfMonth, day.monthValue - 1, day.year
                ).collect {
                    if (it is Response.Success && !it.data.isNullOrEmpty()) {
                        listOfListWaterRecord.add(it.data)
                    } else {
                        listOfListWaterRecord.add(emptyList())
                    }
                    updateWaterDayReportList(listOfListWaterRecord)
                }
            }
            _uiState.update { it.copy(isLoadingData = Response.Success(true)) }
        }
    }

    private fun updateWaterDayReportList(listOfListWaterRecord: ArrayList<List<WaterRecordEntity>>) {
        val reportList = getDefaultDayReportListByWeek()
        repeat(listOfListWaterRecord.size) {
            if (listOfListWaterRecord[it].isNotEmpty()) {
                reportList[it] = createNewDayReportBasedOnRecordList(listOfListWaterRecord[it])
            }
        }
        _uiState.update {
            it.copy(dayReportList = reportList)
        }
    }

    private fun getDefaultDayReportListByWeek(): ArrayList<WaterDayReport> {
        val result = arrayListOf<WaterDayReport>()
        for (i in 1..7) {
            result.add(
                WaterDayReport(
                    DEFAULT_WATER_TARGET_AMOUNT, DEFAULT_DRANK_AMOUNT, getDayOfWeekPrefix(i)
                )
            )
        }
        return result
    }

    private fun createNewDayReportBasedOnRecordList(recordList: List<WaterRecordEntity>): WaterDayReport {
        var drankAmount = 0
        var currentTarget = 0
        for (item in recordList) {
            drankAmount += item.amount
            if (item.currentTarget > currentTarget) {
                currentTarget = item.currentTarget
            }
        }
        return WaterDayReport(
            targetAmount = currentTarget,
            drankAmount = drankAmount,
            dayOfWeek = getDayOfWeekPrefix(recordList[0].dateTime.get(Calendar.DAY_OF_WEEK))
        )
    }

    private fun updateFirstAndLastDayOfWeekWithGivenDay(date: LocalDate) {
        updateFirstAndLastDayOfWeekWithFirstAndLastDay(
            getFirstDayOfWeekWithGivenDate(date), getLastDayOfWeekWithGivenDay(date)
        )
    }

    private fun updateFirstAndLastDayOfWeekWithFirstAndLastDay(
        firstDay: LocalDate, lastDay: LocalDate
    ) {
        val lastDayString = getDayFormatWithYear(lastDay)
        val firstDayString = if (firstDay.year == lastDay.year) {
            if (firstDay.month == lastDay.month) {
                firstDay.dayOfMonth.toString()
            } else {
                getDayFormatWithOnlyMonthPrefix(firstDay)
            }
        } else {
            getDayFormatWithYear(firstDay)
        }
        _uiState.update {
            it.copy(
                firstDayOfWeek = firstDayString, lastDayOfWeek = lastDayString
            )
        }
    }
}
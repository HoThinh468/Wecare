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
import com.vn.wecare.utils.WecareUserConstantValues.KCAL_TO_CAL
import com.vn.wecare.utils.WecareUserConstantValues.NUMBER_OF_DAYS_IN_WEEK
import com.vn.wecare.utils.WecareUserConstantValues.ONE_HUNDRED_PERCENT_VALUE
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
    val dayReportList: ArrayList<WaterDayReport> = arrayListOf(),
    val isAbleToShowBarChart: Boolean = false,
    val isNextClickEnable: Boolean = false,
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
        fetchWaterDayRecordDataFromFirebase()
        updateIfViewNextWeekEnable()
    }

    fun onNextWeekClick() {
        desiredViewDay.value = desiredViewDay.value.plusDays(7)
        updateFirstAndLastDayOfWeekWithGivenDay(desiredViewDay.value)
        fetchWaterDayRecordDataFromFirebase()
        updateIfViewNextWeekEnable()
    }

    private fun updateIfViewNextWeekEnable() {
        _uiState.update {
            it.copy(
                isNextClickEnable = desiredViewDay.value.dayOfYear != LocalDate.now().dayOfYear
            )
        }
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
        updateAverageDrankAmount(reportList)
        updateAverageLevel(reportList)
        updateCaloriesBurnedBasedOnTotalDrankAmount(reportList)
        checkIfWaterDayReportListIsNotDefaultValue(reportList)
    }

    private fun checkIfWaterDayReportListIsNotDefaultValue(reportList: List<WaterDayReport>) {
        var isDefaultValue = true
        reportList.forEach {
            if (it.drankAmount != 0) {
                isDefaultValue = false
                return@forEach
            }
        }
        _uiState.update { it.copy(isAbleToShowBarChart = !isDefaultValue) }
    }

    private fun getDefaultDayReportListByWeek(): ArrayList<WaterDayReport> {
        val result = arrayListOf<WaterDayReport>()
        for (i in 1..NUMBER_OF_DAYS_IN_WEEK) {
            result.add(
                WaterDayReport(
                    DEFAULT_WATER_TARGET_AMOUNT, DEFAULT_DRANK_AMOUNT, getDayOfWeekPrefix(i)
                )
            )
        }
        return result
    }

    private fun updateAverageDrankAmount(reportLis: List<WaterDayReport>) {
        _uiState.update {
            it.copy(
                averageAmount = getTotalDrankAmountWithWaterDayReportList(
                    reportLis
                ) / NUMBER_OF_DAYS_IN_WEEK
            )
        }
    }

    private fun updateAverageLevel(reportList: List<WaterDayReport>) {
        _uiState.update {
            it.copy(
                averageLevel = ((getTotalDrankAmountWithWaterDayReportList(reportList).toFloat() / getTotalTargetAmountWithWaterDayReportList(
                    reportList
                ).toFloat()) * ONE_HUNDRED_PERCENT_VALUE).toInt()
            )
        }
    }

    private fun updateCaloriesBurnedBasedOnTotalDrankAmount(reportList: List<WaterDayReport>) {
        _uiState.update {
            it.copy(
                caloriesBurnt = (getTotalDrankAmountWithWaterDayReportList(
                    reportList
                ).toFloat() / KCAL_TO_CAL) * 20
            )
        }
    }

    private fun getTotalDrankAmountWithWaterDayReportList(reportLis: List<WaterDayReport>): Int {
        var totalDrankAmount = 0
        reportLis.forEach {
            totalDrankAmount += it.drankAmount
        }
        return totalDrankAmount
    }

    private fun getTotalTargetAmountWithWaterDayReportList(reportLis: List<WaterDayReport>): Int {
        var totalTarget = 0
        reportLis.forEach {
            totalTarget += it.targetAmount
        }
        return totalTarget
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
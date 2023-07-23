package com.vn.wecare.feature.food.mealplan.weekly

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.vn.wecare.utils.WecareUserConstantValues.NUMBER_OF_DAYS_IN_WEEK
import com.vn.wecare.utils.getDayFormatWithOnlyMonthPrefix
import com.vn.wecare.utils.getDayFormatWithYear
import com.vn.wecare.utils.getFirstDayOfWeekWithGivenDate
import com.vn.wecare.utils.getLastDayOfWeekWithGivenDay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

data class WeeklyMealPlanUIState(
    val firstDayOfWeek: String = "",
    val lastDayOfWeek: String = "",
    val isNextClickEnable: Boolean = false
)

class WeeklyMealPlanViewModel : ViewModel() {

    private var desiredViewDay = mutableStateOf(LocalDate.now())

    private val _uiState = MutableStateFlow(WeeklyMealPlanUIState())
    val uiState = _uiState.asStateFlow()

    private val _localDateList = MutableStateFlow(emptyList<LocalDate>())
    val localDateList = _localDateList.asStateFlow()

    init {
        updateFirstAndLastDayOfWeekWithGivenDay(LocalDate.now())
        updateListOfLocalDate()
    }

    fun onPreviousWeekClick() {
        desiredViewDay.value = desiredViewDay.value.minusDays(NUMBER_OF_DAYS_IN_WEEK.toLong())
        updateFirstAndLastDayOfWeekWithGivenDay(desiredViewDay.value)
        updateIfViewNextWeekEnable()
        updateListOfLocalDate()
    }

    fun onNextWeekClick() {
        desiredViewDay.value = desiredViewDay.value.plusDays(NUMBER_OF_DAYS_IN_WEEK.toLong())
        updateFirstAndLastDayOfWeekWithGivenDay(desiredViewDay.value)
        updateIfViewNextWeekEnable()
        updateListOfLocalDate()
    }

    private fun updateFirstAndLastDayOfWeekWithGivenDay(date: LocalDate) {
        updateFirstAndLastDayOfWeekWithFirstAndLastDay(
            getFirstDayOfWeekWithGivenDate(date), getLastDayOfWeekWithGivenDay(date)
        )
    }

    private fun updateIfViewNextWeekEnable() {
        _uiState.update {
            it.copy(
                isNextClickEnable = desiredViewDay.value.dayOfYear != LocalDate.now().dayOfYear
            )
        }
    }

    private fun updateListOfLocalDate() {
        val firstDayOfWeek = getFirstDayOfWeekWithGivenDate(desiredViewDay.value)
        val listOfDate = arrayListOf<LocalDate>()
        for (i in 0..6) {
            listOfDate.add(firstDayOfWeek.plusDays(i.toLong()))
        }
        _localDateList.update { listOfDate }
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
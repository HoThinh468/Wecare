package com.vn.wecare.feature.home.step_count

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.WecareUserSingletonObject
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.ext.toDD_MM_yyyy
import com.vn.wecare.core.model.HistoryItem
import com.vn.wecare.feature.exercises.history.oneDayInMillis
import com.vn.wecare.feature.exercises.history.oneWeekInMillis
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.step_count.data.entity.toModel
import com.vn.wecare.feature.home.step_count.data.model.StepsPerDay
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import com.vn.wecare.feature.home.step_count.data.model.fromDayIdToMillis
import com.vn.wecare.feature.home.step_count.usecase.GetCurrentStepsFromSensorUsecase
import com.vn.wecare.feature.home.step_count.usecase.GetStepsPerDayUsecase
import com.vn.wecare.feature.home.step_count.usecase.GetStepsPerHourWithDayIdUsecase
import com.vn.wecare.utils.WecareUserConstantValues
import com.vn.wecare.utils.getCurrentDayId
import com.vn.wecare.utils.getDayId
import com.vn.wecare.utils.getFirstWeekdayTimestamp
import com.vn.wecare.utils.getLastWeekdayTimestamp
import com.vn.wecare.utils.getMonthPrefix
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class StepsCountUiState(
    val currentSteps: Int = 0,
    val caloConsumed: Int = 0,
    val moveMin: Int = 0,
    val isLoading: Boolean = false,
    val selectedDay: String = "",
    val hasData: Boolean = true,
    val hoursList: List<StepsPerHour> = emptyList(),
    val stepGoal: Int = 0,
    val caloriesBurnedGoal: Int = 0,
    val moveTimeGoal: Int = 0
)

//data class DashboardCaloriesUiState(
//    val remainedCalories: Int = 0,
//    val caloriesIn: Int = 0,
//    val caloriesInProgress: Float = 0f,
//    val caloriesOut: Int = 0,
//    val caloriesOutProgress: Float = 0f,
//)

@HiltViewModel
class StepCountViewModel @Inject constructor(
    private val getCurrentStepsFromSensorUsecase: GetCurrentStepsFromSensorUsecase,
    private val getStepsPerDayUsecase: GetStepsPerDayUsecase,
    private val getStepsPerHourWithDayIdUsecase: GetStepsPerHourWithDayIdUsecase,
) : ViewModel() {

    // Define a variable of ui state
    private val _stepsCountUiState = MutableStateFlow(StepsCountUiState())
    val stepsCountUiState: StateFlow<StepsCountUiState> get() = _stepsCountUiState

//    private val currentDate = LocalDate.now()
//
//    private val _dashboardCaloriesUiState = MutableStateFlow(DashboardCaloriesUiState())
//    val dashboardCaloriesUiState = _dashboardCaloriesUiState.asStateFlow()
//
//    fun initDashboardUiState() {
//        initCaloriesOverviewUi()
//    }
//
//    private fun initCaloriesOverviewUi() {
//        val caloriesObj = WecareCaloriesObject.getInstance()
//        updateCaloriesIn(caloriesObj.caloriesInEachDay)
//    }
//
//    private fun updateCaloriesIn(caloriesInGoal: Int) = viewModelScope.launch {
//        getTotalInputCaloriesUsecase.getTotalInputCaloriesOfEachDay(
//            currentDate.dayOfMonth, currentDate.monthValue - 1, currentDate.year
//        ).collect { res ->
//            if (res is Response.Success) {
//                _dashboardCaloriesUiState.update {
//                    it.copy(
//                        caloriesIn = res.data, caloriesInProgress = getProgressInFloatWithIntInput(
//                            res.data, caloriesInGoal
//                        )
//                    )
//                }
//            } else {
//                _dashboardCaloriesUiState.update {
//                    it.copy(caloriesIn = 0, caloriesInProgress = 0f)
//                }
//            }
//        }
//    }

    var listHistoryResponse by mutableStateOf<Response<List<StepsPerDay>>>(Response.Loading)
    private val _listHistory = MutableStateFlow<List<StepsPerDay>?>(null)
    val listHistory: StateFlow<List<StepsPerDay>?>
        get() = _listHistory

    private val _listHistoryDisplay = MutableStateFlow<List<StepsPerDay>?>(null)
    val listHistoryDisplay: StateFlow<List<StepsPerDay>?>
        get() = _listHistoryDisplay

    private var _historyViewTime = MutableStateFlow(System.currentTimeMillis())
    val historyViewTime: StateFlow<Long>
        get() = _historyViewTime

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

    fun loadListHistory() = viewModelScope.launch {
        getStepsPerDayUsecase.getListStepsHistory().collect { response ->
            listHistoryResponse = response
            if (listHistoryResponse is Response.Success) {
                _listHistory.value =
                    (listHistoryResponse as Response.Success<List<StepsPerDay>>).data
                filterListHistory()
                checkIsNextBtnEnable()
            }
        }
    }

    private fun checkIsNextBtnEnable() {
        _isNextBtnEnable.value =
            _historyViewTime.value + oneWeekInMillis < getLastWeekdayTimestamp(System.currentTimeMillis())
    }

    private fun filterListHistory() {
        val list = _listHistory.value ?: return
        val listDisplay = mutableListOf<StepsPerDay>()
        val listChart = mutableListOf(0f, 0f, 0f, 0f, 0f, 0f, 0f)
        for (i in list) {
            if (i.dayId.fromDayIdToMillis() >= getFirstWeekdayTimestamp(_historyViewTime.value)
                && i.dayId.fromDayIdToMillis() < getFirstWeekdayTimestamp(_historyViewTime.value) + oneWeekInMillis
            ) {
                listDisplay.add(i)
                for (j in 0..6) {
                    if (i.dayId.fromDayIdToMillis() >= getFirstWeekdayTimestamp(_historyViewTime.value) + j * oneDayInMillis
                        && i.dayId.fromDayIdToMillis() < getFirstWeekdayTimestamp(_historyViewTime.value) + (j + 1) * oneDayInMillis
                    ) {
                        listChart[j] = listChart[j] + i.steps
                    }
                }
            }
        }
        _listChartDisplay.value = listChart
        _listHistoryDisplay.value = listDisplay
    }

    init {
        updateCurrentSteps(getCurrentStepsFromSensorUsecase.getCurrentStepsFromSensor())
        loadListHistory()
    }

    fun initUIState() {
        updateCurrentSteps(getCurrentStepsFromSensorUsecase.getCurrentStepsFromSensor())
        initializeGoalIndex()
        loadListHistory()
    }

    fun updateCurrentSteps(stepsFromSensor: Float) = viewModelScope.launch {
        val info = WecareUserSingletonObject.getInstance()
        getStepsPerDayUsecase.getStepsInPreviousDay(
            StepsPerDay(
                dayId = System.currentTimeMillis().minus(86400000L).toDD_MM_yyyy(),
                steps = stepsFromSensor.toInt(),
                calories = stepsFromSensor.toInt().getCaloriesBurnedFromStepCount(info.height ?: 1, info.weight ?: 1),
                moveTime = stepsFromSensor.toInt().getMoveTimeFromStepCount(info.height ?: 1)
            )
        ).collect { response ->
            if (response is Response.Success) {
                val stepsInPreviousDay = response.data
                val steps = stepsFromSensor.toInt() - stepsInPreviousDay.steps
                val caloriesBurned = steps.getCaloriesBurnedFromStepCount(info.height ?: 1, info.weight ?: 1)
                val moveTime = steps.getMoveTimeFromStepCount(info.height ?: 1)
                _stepsCountUiState.update {
                    if(stepsFromSensor == 0f) {
                        it.copy(
                            currentSteps = 0,
                            caloConsumed = 0,
                            moveMin = 0
                        )
                    } else {
                        it.copy(
                            currentSteps = steps,
                            caloConsumed = caloriesBurned,
                            moveMin = moveTime
                        )
                    }
                }
            }
        }
    }

    private fun updateStepsPerDayWithHours(year: Int, month: Int, dayOfMonth: Int) {
        viewModelScope.launch {
            val hoursList = mutableListOf<StepsPerHour>()
            getStepsPerHourWithDayIdUsecase.getStepsPerHourWithDayId(
                dayId = getDayId(dayOfMonth, month, year)
            ).collect { list ->
                if (list.isNotEmpty()) {
                    list.forEach { stepsPerDayWithHours ->
                        if (stepsPerDayWithHours != null) {
                            hoursList.add(stepsPerDayWithHours.toModel())
                        }
                    }
                    _stepsCountUiState.update { it.copy(hoursList = hoursList) }
                } else {
                    _stepsCountUiState.update { it.copy(hoursList = emptyList()) }
                }
            }
        }
    }

    fun onDaySelected(year: Int, month: Int, dayOfMonth: Int) {
        updateDateTitle(dayOfMonth, month, year)
        if (getCurrentDayId() == getDayId(dayOfMonth, month, year)) {
            updateCurrentSteps(getCurrentStepsFromSensorUsecase.getCurrentStepsFromSensor())
        } else {
//            viewModelScope.launch {
//                getStepsPerDayUsecase.getStepsPerDayWithDayId(getDayId(dayOfMonth, month, year))
//                    .collect { stepsPerDay ->
//                        if (stepsPerDay != null) {
//                            _stepsCountUiState.update { ui ->
//                                ui.copy(
//                                    currentSteps = stepsPerDay.steps,
//                                    caloConsumed = stepsPerDay.toModel().calories,
//                                    moveMin = stepsPerDay.toModel().moveTime,
//                                    hasData = true
//                                )
//                            }
//                            updateStepsPerDayWithHours(year, month, dayOfMonth)
//                        } else {
//                            _stepsCountUiState.update {
//                                it.copy(hasData = false)
//                            }
//                        }
//                    }
//            }
        }
    }

    private fun initializeGoalIndex() {
        viewModelScope.launch {
            LatestGoalSingletonObject.getInStanceFlow().collect { goals ->
                _stepsCountUiState.update {
                    it.copy(
                        stepGoal = goals.stepsGoal,
                        caloriesBurnedGoal = getCaloriesToBurnGoal(goals.goalName),
                        moveTimeGoal = goals.moveTimeGoal
                    )
                }
            }
        }
    }

    private fun updateDateTitle(day: Int, month: Int, year: Int) {
        _stepsCountUiState.update {
            it.copy(
                selectedDay = "${getMonthPrefix(month)} $day, $year"
            )
        }
    }

    private fun getCaloriesToBurnGoal(goal: String): Int {
        return when (goal) {
            EnumGoal.GAINMUSCLE.value -> WecareUserConstantValues.DEFAULT_CALORIES_TO_BURN_EACH_DAY_TO_GAIN_MUSCLE
            EnumGoal.LOSEWEIGHT.value -> WecareUserConstantValues.DEFAULT_CALORIES_TO_BURN_EACH_DAY_TO_LOSE_WEIGHT
            else -> WecareUserConstantValues.DEFAULT_CALORIES_TO_BURN_EACH_DAY
        }
    }
}
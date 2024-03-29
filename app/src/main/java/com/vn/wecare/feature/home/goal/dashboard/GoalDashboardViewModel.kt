package com.vn.wecare.feature.home.goal.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.data.model.Goal
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.feature.home.goal.usecase.GetGoalWeeklyRecordUsecase
import com.vn.wecare.feature.home.goal.usecase.GetGoalsFromFirebaseUsecase
import com.vn.wecare.feature.home.goal.usecase.UpdateGoalStatusUsecase
import com.vn.wecare.feature.home.goal.utils.getDayFromLongWithFormat
import com.vn.wecare.utils.WecareUserConstantValues.DAY_TO_MILLISECONDS
import com.vn.wecare.utils.getProgressInFloatWithIntInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

data class GoalDashboardAppbarUiState(
    val goalName: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val imgSrc: Int = R.drawable.img_illu_maintain_weight,
)

data class GoalDetailUiState(
    val goalName: String = "",
    val description: String = "",
    val dayLeft: Int = 0,
    val timeProgress: Float = 0f,
    val caloriesInGoal: Int = 0,
    val caloriesOutGoal: Int = 0,
    val caloriesRecommend: Int = 0,
    val stepRecommend: Int = 0,
    val activeTimeRecommend: Int = 0,
    val status: String = "",
    val weightToLose: Int = 0,
    val timeToReachGoal: Int = 0,
    val weeklyGoalWeight: Float = 0f
)

data class UpdateGoalStatusUiState(
    val isCancelGoalEnabled: Boolean = false, val updateResponse: Response<Boolean>? = null
)

data class RecordUiState(
    val getRecordsResponse: Response<Boolean>? = null,
    val records: List<GoalWeeklyRecord> = emptyList()
)

@HiltViewModel
class GoalDashboardViewModel @Inject constructor(
    private val getGoalsFromFirebaseUsecase: GetGoalsFromFirebaseUsecase,
    private val getGoalWeeklyRecordUsecase: GetGoalWeeklyRecordUsecase,
    private val updateGoalStatusUsecase: UpdateGoalStatusUsecase
) : ViewModel() {

    private val _isInternetAvailable = MutableStateFlow(true)

    fun checkIfInternetIsAvailable(networkAvailable: Boolean) {
        _isInternetAvailable.value = networkAvailable
    }

    private val _appbarUi = MutableStateFlow(GoalDashboardAppbarUiState())
    val appbarUi = _appbarUi.asStateFlow()

    private val _detailUi = MutableStateFlow(GoalDetailUiState())
    val detailUi = _detailUi.asStateFlow()

    private val _recordUi = MutableStateFlow(RecordUiState())
    val recordUi = _recordUi.asStateFlow()

    private val _updateGoalUi = MutableStateFlow(UpdateGoalStatusUiState())
    val updateGoalUi = _updateGoalUi.asStateFlow()

    private val _goal = MutableStateFlow(Goal())

    fun initUI() {
        updateLatestGoal()
        initRecordUi()
        initGoalDashboardAppbarUi()
        initDetailUi()
    }

    fun updateGoalStatusToCanceled() = viewModelScope.launch {
        _updateGoalUi.update { it.copy(updateResponse = Response.Loading) }
        updateGoalStatusUsecase.update(_goal.value.goalId, GoalStatus.CANCELED).collect { res ->
            _updateGoalUi.update { it.copy(updateResponse = res) }
            if (res is Response.Success) {
                _updateGoalUi.update { it.copy(isCancelGoalEnabled = false) }
                _detailUi.update { it.copy(status = GoalStatus.CANCELED.value) }
                LatestGoalSingletonObject.updateInStance(_goal.value.copy(goalStatus = GoalStatus.CANCELED.value))
                updateGoalStatusUsecase.updateWeeklyGoalStatusWhenGoalIsCanceled(_goal.value.goalId)
            }
        }
    }

    private fun updateLatestGoal() = viewModelScope.launch {
        if (_isInternetAvailable.value) {
            getGoalsFromFirebaseUsecase.getCurrentGoalFromFirebase().collect { res ->
                if (res is Response.Success) {
                    LatestGoalSingletonObject.updateInStance(res.data)
                    _goal.update { res.data }
                }
            }
        }
    }

    private fun initGoalDashboardAppbarUi() = viewModelScope.launch {
        _goal.collect { goal ->
            _appbarUi.update {
                it.copy(
                    goalName = goal.goalName,
                    startDate = getDayFromLongWithFormat(goal.dateSetGoal),
                    endDate = getDayFromLongWithFormat(goal.dateEndGoal),
                    imgSrc = EnumGoal.getEnumGoalFromValue(goal.goalName).imgRes
                )
            }
        }
    }

    private fun getIllustrationImgSrcBasedOnGoal(goal: String): Int {
        return when (goal) {
            EnumGoal.GAINMUSCLE.value -> R.drawable.img_illu_gain_weight
            EnumGoal.LOSEWEIGHT.value -> R.drawable.img_illu_loose_weight
            EnumGoal.GETHEALTHIER.value -> R.drawable.img_illu_maintain_weight
            else -> R.drawable.img_illu_improve_mood
        }
    }

    private fun initDetailUi() = viewModelScope.launch {
        _goal.collect { goal ->
            _detailUi.update {
                it.copy(
                    goalName = goal.goalName,
                    description = getGoalDescription(
                        goal.goalName, goal.weightDifference, goal.timeToReachGoalInWeek
                    ),
                    dayLeft = getDayLeft(goal.dateEndGoal),
                    timeProgress = getTimeProgress(goal.dateEndGoal, goal.dateSetGoal),
                    caloriesInGoal = goal.caloriesInEachDayGoal,
                    caloriesOutGoal = goal.caloriesBurnedEachDayGoal,
                    caloriesRecommend = goal.caloriesBurnedGoalForStepCount,
                    stepRecommend = goal.stepsGoal,
                    activeTimeRecommend = goal.moveTimeGoal,
                    status = goal.goalStatus,
                    weightToLose = goal.weightDifference,
                    timeToReachGoal = goal.timeToReachGoalInWeek,
                    weeklyGoalWeight = goal.weeklyGoalWeight
                )
            }
            _updateGoalUi.update {
                it.copy(isCancelGoalEnabled = goal.goalStatus == GoalStatus.INPROGRESS.value)
            }
        }
    }

    private fun getGoalDescription(name: String, weightDiffer: Int, time: Int): String {
        return when (name) {
            EnumGoal.GAINWEIGHT.value -> "You want to gain $weightDiffer kg in $time week(s)"
            EnumGoal.LOSEWEIGHT.value -> "You want to loose $weightDiffer kg in $time week(s)"
            else -> "$name in $time week(s)"
        }
    }

    private fun getDayLeft(endDay: Long): Int {
        val now = System.currentTimeMillis()
        return if (endDay > now) {
            (abs(endDay - System.currentTimeMillis()) / DAY_TO_MILLISECONDS).toInt()
        } else 0
    }

    private fun getTimeProgress(endDay: Long, startDay: Long): Float {
        val totalDay = ((endDay - startDay) / DAY_TO_MILLISECONDS).toInt()
        val dayPassed = totalDay - getDayLeft(endDay)
        return getProgressInFloatWithIntInput(dayPassed, totalDay)
    }

    private fun initRecordUi() = viewModelScope.launch {
        _recordUi.update { it.copy(getRecordsResponse = Response.Loading) }
        getGoalWeeklyRecordUsecase.getAll(LatestGoalSingletonObject.getInStance().goalId)
            .collect { res ->
                Log.d(
                    GoalDashboardFragment.goalDashboardTag, "Get all weekly records response $res"
                )
                if (res is Response.Success) {
                    _recordUi.update {
                        it.copy(
                            records = res.data, getRecordsResponse = Response.Success(true)
                        )
                    }
                } else {
                    _recordUi.update { it.copy(getRecordsResponse = Response.Error(Exception("Fail to get records data"))) }
                }
            }
    }

    fun resetGetResponseData() {
        _recordUi.update { it.copy(getRecordsResponse = null) }
    }
}
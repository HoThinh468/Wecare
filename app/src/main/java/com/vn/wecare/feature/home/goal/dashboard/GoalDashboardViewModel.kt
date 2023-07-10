package com.vn.wecare.feature.home.goal.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.usecase.GetGoalsFromFirebaseUsecase
import com.vn.wecare.feature.home.goal.utils.getDayFromLongWithFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GoalDashboardAppbarUiState(
    val goalName: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val imgSrc: Int = R.drawable.img_illu_healthier,
)

@HiltViewModel
class GoalDashboardViewModel @Inject constructor(
    private val getGoalsFromFirebaseUsecase: GetGoalsFromFirebaseUsecase
) : ViewModel() {

    private val _isInternetAvailable = MutableStateFlow(true)

    fun checkIfInternetIsAvailable(networkAvailable: Boolean) {
        _isInternetAvailable.value = networkAvailable
    }

    private val _appbarUi = MutableStateFlow(GoalDashboardAppbarUiState())
    val appbarUi = _appbarUi.asStateFlow()

    fun initUI() {
        initGoalDashboardAppbarUi()
    }

    private fun initGoalDashboardAppbarUi() = viewModelScope.launch {
        if (_isInternetAvailable.value) {
            getGoalsFromFirebaseUsecase.getCurrentGoalFromFirebase().collect {
                if (it is Response.Success) {
                    LatestGoalSingletonObject.updateInStance(it.data)
                }
            }
        }
        LatestGoalSingletonObject.getInStanceFlow().collect { goal ->
            _appbarUi.update {
                it.copy(
                    goalName = goal.goalName,
                    startDate = getDayFromLongWithFormat(goal.dateSetGoal),
                    endDate = getDayFromLongWithFormat(goal.dateEndGoal),
                    imgSrc = getIllustrationImgSrcBasedOnGoal(goal.goalName)
                )
            }
        }
    }

    private fun getIllustrationImgSrcBasedOnGoal(goal: String): Int {
        return when (goal) {
            EnumGoal.GAINMUSCLE.value -> R.drawable.img_illu_muscle
            EnumGoal.LOSEWEIGHT.value -> R.drawable.img_illu_loose_weight
            EnumGoal.GETHEALTHIER.value -> R.drawable.img_illu_healthier
            else -> R.drawable.img_illu_improve_mood
        }
    }
}
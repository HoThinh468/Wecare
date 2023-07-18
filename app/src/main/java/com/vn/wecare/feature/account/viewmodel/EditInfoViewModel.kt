package com.vn.wecare.feature.account.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.WecareUserSingletonObject
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.data.UserRepository
import com.vn.wecare.feature.account.data.model.WecareUser
import com.vn.wecare.feature.account.view.editinfo.EditInformationFragment
import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.feature.home.bmi.usecase.BMIUseCase
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.ActivityLevel
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.data.model.Goal
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import com.vn.wecare.feature.home.goal.usecase.DefineGoalBasedOnInputsUsecase
import com.vn.wecare.feature.home.goal.usecase.SaveGoalsToFirebaseUsecase
import com.vn.wecare.feature.home.goal.usecase.SetupGoalWeeklyRecordsWhenCreateNewGoalUsecase
import com.vn.wecare.feature.home.goal.utils.getGoalEnumWithName
import com.vn.wecare.feature.onboarding.viewmodel.OnboardingDialogUiState
import com.vn.wecare.utils.WecareUserConstantValues.MAX_AGE
import com.vn.wecare.utils.WecareUserConstantValues.MAX_HEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MAX_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_AGE
import com.vn.wecare.utils.WecareUserConstantValues.MIN_HEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.NUMBER_OF_DAYS_IN_WEEK
import com.vn.wecare.utils.isValidUsername
import com.vn.wecare.utils.toIntSafely
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditGeneralInfoUiState(
    val currentChosenGender: Int = 0,
    val isNewInfoDifferent: Boolean = false,
    val isUserNameValid: Boolean = true,
    val isHeightValid: Boolean = true,
    val isWeightValid: Boolean = true,
    val isAgeValid: Boolean = true,
    val updateInfoResult: Response<Boolean>? = null,
)

data class EditGoalInfoUiState(
    val desiredWeightDifferencePicker: Int = 1,
    val isGoalExpired: Boolean = false,
    val currentChosenGoal: EnumGoal = EnumGoal.MAINTAINWEIGHT,
    val doNotChooseGoal: EnumGoal = EnumGoal.MAINTAINWEIGHT,
    val currentChosenActivityLevel: ActivityLevel = ActivityLevel.ACTIVE,
    val weeklyGoalWeight: Float = 0f
)

@HiltViewModel
class EditInfoViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val defineGoalBasedOnInputsUsecase: DefineGoalBasedOnInputsUsecase,
    private val saveGoalsToFirebaseUsecase: SaveGoalsToFirebaseUsecase,
    private val setupGoalWeeklyRecordsWhenCreateNewGoalUsecase: SetupGoalWeeklyRecordsWhenCreateNewGoalUsecase,
    private val bmiUsecase: BMIUseCase
) : ViewModel() {

    private val _editGeneralInfoUiState = MutableStateFlow(EditGeneralInfoUiState())
    val editInfoUiState = _editGeneralInfoUiState.asStateFlow()

    private val _onboardingDialogUiState = MutableStateFlow(OnboardingDialogUiState())
    val onboardingDialogUiState = _onboardingDialogUiState.asStateFlow()

    private val _editGoalInfoUiState = MutableStateFlow(EditGoalInfoUiState())
    val editGoalInfoUiState = _editGoalInfoUiState.asStateFlow()

    fun initEditInfoScreenUiState(goal: Goal) = viewModelScope.launch {
        checkIfGoalIsExpired()
        onPickDesiredWeightDifferenceScroll(goal.weightDifference)
        updateCurrentGoalWhenInit(goal.goalName)
        onWeeklyGoalWeightSelected(goal.weeklyGoalWeight)
        WecareUserSingletonObject.getInstanceFlow().collect {
            onUserNameChange(it.userName)
            onHeightChange(it.height.toString())
            onWeightChange(it.weight.toString())
            onAgeChange(it.age.toString())
            updateCurrentGenderWhenInit(it.gender ?: true)
            onActivityLevelSelected(ActivityLevel.getActivityLevelFromValue(it.activityLevel))
        }
    }

    var userName by mutableStateOf("")
    fun onUserNameChange(newVal: String) {
        userName = newVal
    }

    fun clearUserName() {
        userName = ""
    }

    var height by mutableStateOf("")
    fun onHeightChange(newVal: String) {
        height = newVal
    }

    fun clearHeight() {
        height = ""
    }

    var weight by mutableStateOf("")
    fun onWeightChange(newVal: String) {
        weight = newVal
    }

    fun clearWeight() {
        weight = ""
    }

    var age by mutableStateOf("")
    fun onAgeChange(newVal: String) {
        age = newVal
    }

    fun clearAge() {
        age = ""
    }

    fun onGoalSelected(goal: EnumGoal) {
        _editGoalInfoUiState.update { it.copy(currentChosenGoal = goal) }
    }

    fun onActivityLevelSelected(level: ActivityLevel) {
        _editGoalInfoUiState.update { it.copy(currentChosenActivityLevel = level) }
    }

    fun onGenderSelected(id: Int) {
        _editGeneralInfoUiState.update { it.copy(currentChosenGender = id) }
    }

    fun onPickDesiredWeightDifferenceScroll(difference: Int) {
        _editGoalInfoUiState.update {
            it.copy(desiredWeightDifferencePicker = difference)
        }
    }

    fun onWeeklyGoalWeightSelected(weight: Float) {
        _editGoalInfoUiState.update { it.copy(weeklyGoalWeight = weight) }
    }

    fun checkIfNewInfoIsDifferent(): WecareUser? {
        val oldUser = WecareUserSingletonObject.getInstance()
        val newUser = WecareUser(
            userId = oldUser.userId,
            emailVerified = oldUser.emailVerified,
            email = oldUser.email,
            userName = userName,
            height = height.toIntSafely(),
            weight = weight.toIntSafely(),
            age = age.toIntSafely(),
            goal = _editGoalInfoUiState.value.currentChosenGoal.value,
            gender = getGenderFromId(_editGeneralInfoUiState.value.currentChosenGender),
            activityLevel = _editGoalInfoUiState.value.currentChosenActivityLevel.value
        )
        _editGeneralInfoUiState.update { it.copy(isNewInfoDifferent = newUser != oldUser) }
        return if (_editGeneralInfoUiState.value.isNewInfoDifferent) newUser else null
    }

    fun onSaveInfoClick(showToast: () -> Unit) {
        checkIfNewInfoIsDifferent()
        if (_editGeneralInfoUiState.value.isNewInfoDifferent) {
            checkIfUserNameValid()
            checkIfHeightValid()
            checkIfWeightValid()
            checkIfAgeValid()
            if (_editGeneralInfoUiState.value.isUserNameValid && _editGeneralInfoUiState.value.isHeightValid && _editGeneralInfoUiState.value.isWeightValid && _editGeneralInfoUiState.value.isAgeValid) {
                if (_onboardingDialogUiState.value.shouldShowRecommendationDialog && _onboardingDialogUiState.value.shouldShowWarningDialog) return
                saveNewUserInfoAndNewGoal()
            }
        } else {
            showToast()
        }
    }

    fun saveNewUserInfoAndNewGoal() {
        val newUser = checkIfNewInfoIsDifferent() ?: return
        Log.d(EditInformationFragment.editTag, "New user $newUser")
        val enumGoal = _editGoalInfoUiState.value.currentChosenGoal
        _editGeneralInfoUiState.update { it.copy(updateInfoResult = Response.Loading) }
        val timeToReachGoal =
            (_editGoalInfoUiState.value.desiredWeightDifferencePicker / _editGoalInfoUiState.value.weeklyGoalWeight).toInt()
        viewModelScope.launch {
            userRepository.insertUserToFirebase(newUser)
            userRepository.insertUserToLocaldb(newUser)
            val goal = defineGoalBasedOnInputsUsecase.getGoalFromInputs(
                goal = enumGoal,
                height = height.toIntSafely(),
                weight = weight.toIntSafely(),
                age = age.toIntSafely(),
                gender = getGenderFromId(_editGeneralInfoUiState.value.currentChosenGender),
                weightDifference = if (enumGoal == EnumGoal.MAINTAINWEIGHT) null else _editGoalInfoUiState.value.desiredWeightDifferencePicker,
                timeToReachGoal = if (enumGoal == EnumGoal.MAINTAINWEIGHT) null else timeToReachGoal,
                weeklyGoalWeight = _editGoalInfoUiState.value.weeklyGoalWeight,
                activityLevel = _editGoalInfoUiState.value.currentChosenActivityLevel
            )
            WecareUserSingletonObject.updateInstance(newUser)
            saveGoalsToFirebaseUsecase.saveGoalsToFirebase(goal).collect { res ->
                if (res is Response.Success) {
                    setupGoalWeeklyRecordsWhenCreateNewGoalUsecase.setup(
                        goal.timeToReachGoalInWeek,
                        goal.goalId,
                        goal.weeklyGoalWeight,
                        goal.caloriesInEachDayGoal * NUMBER_OF_DAYS_IN_WEEK
                    )
                    bmiUsecase.addBMIHistory(
                        newUser.age ?: MIN_AGE,
                        newUser.gender ?: true,
                        newUser.height,
                        newUser.weight
                    )
                    LatestGoalSingletonObject.updateInStance(goal)
                }
                _editGeneralInfoUiState.update {
                    it.copy(updateInfoResult = res)
                }
            }
            WecareCaloriesObject.updateUserCaloriesAmount()
        }
    }

    fun resetUiState() {
        _editGeneralInfoUiState.update { EditGeneralInfoUiState() }
    }

    private fun updateCurrentGoalWhenInit(goal: String) {
        val goalEnum = getGoalEnumWithName(goal)
        _editGoalInfoUiState.update { it.copy(currentChosenGoal = goalEnum) }
    }

    private fun updateCurrentGenderWhenInit(gender: Boolean) {
        val id = if (gender) 0 else 1
        _editGeneralInfoUiState.update { it.copy(currentChosenGender = id) }
    }

    private fun getGenderFromId(id: Int): Boolean {
        return id == 0
    }

    private fun checkIfUserNameValid() {
        _editGeneralInfoUiState.update { it.copy(isUserNameValid = userName.isValidUsername()) }
    }

    private fun checkIfHeightValid() {
        _editGeneralInfoUiState.update { it.copy(isHeightValid = height.toIntSafely() in MIN_HEIGHT..MAX_HEIGHT) }
    }

    private fun checkIfWeightValid() {
        _editGeneralInfoUiState.update { it.copy(isWeightValid = weight.toIntSafely() in MIN_WEIGHT..MAX_WEIGHT) }
    }

    private fun checkIfAgeValid() {
        _editGeneralInfoUiState.update { it.copy(isAgeValid = age.toIntSafely() in MIN_AGE..MAX_AGE) }
    }

    private fun checkIfGoalIsExpired() {
        _editGoalInfoUiState.update {
            it.copy(
                isGoalExpired = LatestGoalSingletonObject.getInStance().goalStatus != GoalStatus.INPROGRESS.value
            )
        }
    }

    fun dismissWarningDialog() {
        _onboardingDialogUiState.update {
            it.copy(shouldShowWarningDialog = false)
        }
    }

    fun dismissRecommendationDialog() {
        _onboardingDialogUiState.update {
            it.copy(shouldShowRecommendationDialog = false)
        }
    }

    fun resetUpdateRes() {
        _editGeneralInfoUiState.update { it.copy(updateInfoResult = null) }
    }
}
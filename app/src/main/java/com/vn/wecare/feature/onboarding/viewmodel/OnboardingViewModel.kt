package com.vn.wecare.feature.onboarding.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.WecareUserSingletonObject
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.usecase.SaveUserToDbUsecase
import com.vn.wecare.feature.home.bmi.util.getBMIWithHeightAndWeight
import com.vn.wecare.feature.home.bmi.util.getWeightWithBMIAndHeight
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.ActivityLevel
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.usecase.DefineGoalBasedOnInputsUsecase
import com.vn.wecare.feature.home.goal.usecase.SaveGoalsToFirebaseUsecase
import com.vn.wecare.feature.home.goal.usecase.SetupGoalWeeklyRecordsWhenCreateNewGoalUsecase
import com.vn.wecare.feature.onboarding.model.BMIState
import com.vn.wecare.utils.WecareUserConstantValues.BMI_FAT_RANGE
import com.vn.wecare.utils.WecareUserConstantValues.BMI_NORMAL_RANGE
import com.vn.wecare.utils.WecareUserConstantValues.BMI_OVERWEIGHT_RANGE
import com.vn.wecare.utils.WecareUserConstantValues.BMI_UNDERWEIGHT_RANGE
import com.vn.wecare.utils.WecareUserConstantValues.MIN_AGE
import com.vn.wecare.utils.WecareUserConstantValues.MIN_HEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_WEIGHT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingUiState(
    val genderSelectionId: Int = 0,
    val agePicker: Int = MIN_AGE,
    val heightPicker: Int = MIN_HEIGHT,
    val weightPicker: Int = MIN_WEIGHT,
    val desiredWeightDifferencePicker: Int = 1,
    val warningMessage: String? = null,
    val selectedGoal: EnumGoal = EnumGoal.LOSEWEIGHT,
    val recommendedGoal: EnumGoal = EnumGoal.MAINTAINWEIGHT,
    val selectedWeeklyGoalWeight: Float = 0f,
    val recommendedWeeklyGoal: Float = 0f,
    val selectedActivityLevel: ActivityLevel = ActivityLevel.SEDENTARY,
    val updateInformationResult: Response<Boolean>? = null,
)

data class OnboardingDialogUiState(
    val shouldShowWarningDialog: Boolean = false,
    val warningDialogTitle: String = "Hold up!",
    val warningDialogMessage: String = "",
    val shouldShowRecommendationDialog: Boolean = false,
    val recommendationDialogTitle: String = "We can do more!",
    val recommendationDialogMessage: String = "",
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val defineGoalBasedOnInputsUsecase: DefineGoalBasedOnInputsUsecase,
    private val saveGoalsToFirebaseUsecase: SaveGoalsToFirebaseUsecase,
    private val setupGoalWeeklyRecordsWhenCreateNewGoalUsecase: SetupGoalWeeklyRecordsWhenCreateNewGoalUsecase,
    private val saveUserToDbUsecase: SaveUserToDbUsecase
) : ViewModel() {

    var currentIndex = mutableStateOf(0)

    fun onNextClick() {
        when (currentIndex.value) {
            4 -> {
                updateBMIState()
                updateRecommendedGoal()
                currentIndex.value++
            }

            5 -> {
                updateShouldShowWarningDialog()
                if (_onboardingDialogUiState.value.shouldShowWarningDialog) return
                if (_onboardingUiState.value.selectedGoal == EnumGoal.MAINTAINWEIGHT) {
                    saveUserInfoToDb()
                    saveGoalToFirestore()
                } else {
                    updateRecommendedWeeklyGoal()
                    currentIndex.value++
                }
            }

            6 -> {
                if (_onboardingUiState.value.warningMessage == null) {
                    saveUserInfoToDb()
                    saveGoalToFirestore()
                }
            }

            else -> currentIndex.value++
        }
    }

    fun onPreviousClick() {
        if (currentIndex.value > 0) {
            currentIndex.value--
        }
    }

    private val _onboardingUiState = MutableStateFlow(OnboardingUiState())
    val onboardingUiState = _onboardingUiState.asStateFlow()

    private val _onboardingDialogUiState = MutableStateFlow(OnboardingDialogUiState())
    val onboardingDialogUiState = _onboardingDialogUiState.asStateFlow()

    private val _bmiState = MutableStateFlow(BMIState.UNDERWEIGHT)

    fun onGenderSelect(id: Int) {
        _onboardingUiState.update {
            it.copy(genderSelectionId = id)
        }
    }

    fun onPickAgeScroll(age: Int) {
        _onboardingUiState.update {
            it.copy(agePicker = age)
        }
    }

    fun onPickHeightScroll(height: Int) {
        _onboardingUiState.update {
            it.copy(heightPicker = height)
        }
    }

    fun onPickWeightScroll(weight: Int) {
        _onboardingUiState.update {
            it.copy(weightPicker = weight)
        }
    }

    fun onPickDesiredWeightDifferenceScroll(difference: Int) {
        _onboardingUiState.update {
            it.copy(desiredWeightDifferencePicker = difference)
        }
        updateWarningMessage()
    }

    fun onGoalSelect(goal: EnumGoal) {
        _onboardingUiState.update {
            it.copy(selectedGoal = goal)
        }
    }

    fun onWeeklyGoalSelect(goal: Float) {
        _onboardingUiState.update {
            it.copy(selectedWeeklyGoalWeight = goal)
        }
    }

    fun onActivityLevelPicked(level: ActivityLevel) {
        _onboardingUiState.update { it.copy(selectedActivityLevel = level) }
    }

    private fun saveUserInfoToDb() {
        _onboardingUiState.value.apply {
            val user = WecareUserSingletonObject.getInstance()
            val newUpdatedUser = user.copy(
                gender = this.genderSelectionId == 0,
                age = this.agePicker,
                height = this.heightPicker,
                weight = this.weightPicker,
                activityLevel = this.selectedActivityLevel.value
            )
            viewModelScope.launch {
                saveUserToDbUsecase.saveUserToFirestoreDb(newUpdatedUser)
                saveUserToDbUsecase.saveUserToLocalDb(newUpdatedUser)
            }
        }
    }

    private fun saveGoalToFirestore() {
        _onboardingUiState.update { it.copy(updateInformationResult = Response.Loading) }
        val enumGoal = _onboardingUiState.value.selectedGoal
        val timeToReachGoal =
            (_onboardingUiState.value.desiredWeightDifferencePicker / _onboardingUiState.value.selectedWeeklyGoalWeight).toInt()
        val goal = defineGoalBasedOnInputsUsecase.getGoalFromInputs(
            goal = enumGoal,
            height = _onboardingUiState.value.heightPicker,
            weight = _onboardingUiState.value.weightPicker,
            age = _onboardingUiState.value.agePicker,
            gender = _onboardingUiState.value.genderSelectionId == 0,
            weightDifference = if (enumGoal == EnumGoal.MAINTAINWEIGHT) null else _onboardingUiState.value.desiredWeightDifferencePicker,
            timeToReachGoal = if (enumGoal == EnumGoal.MAINTAINWEIGHT) null else timeToReachGoal,
            weeklyGoalWeight = _onboardingUiState.value.selectedWeeklyGoalWeight
        )

        viewModelScope.launch {
            _onboardingUiState.update { it.copy(updateInformationResult = Response.Loading) }
            saveGoalsToFirebaseUsecase.saveGoalsToFirebase(goal).collect { res ->
                if (res is Response.Success) {
                    setupGoalWeeklyRecordsWhenCreateNewGoalUsecase.setup(
                        timeToReachGoal, goal.goalId
                    )
                    LatestGoalSingletonObject.updateInStance(goal)
                }
                _onboardingUiState.update { it.copy(updateInformationResult = res) }
            }
        }
    }

    fun clearOnboardingResult() {
        _onboardingUiState.update { OnboardingUiState() }
        currentIndex.value = 0
    }

    private fun updateBMIState() {
        val bmi = getBMIWithHeightAndWeight(
            _onboardingUiState.value.heightPicker.toFloat() / 100,
            _onboardingUiState.value.weightPicker.toFloat()
        )
        val bmiState = when (bmi) {
            in BMI_UNDERWEIGHT_RANGE -> BMIState.UNDERWEIGHT
            in BMI_NORMAL_RANGE -> BMIState.NORMAL
            in BMI_OVERWEIGHT_RANGE -> BMIState.OVERWEIGHT
            in BMI_FAT_RANGE -> BMIState.FAT
            else -> BMIState.OBESITY
        }
        _bmiState.update { bmiState }
    }

    private fun updateRecommendedGoal() {
        val goal = when (_bmiState.value) {
            BMIState.UNDERWEIGHT -> EnumGoal.GAINWEIGHT
            BMIState.NORMAL -> EnumGoal.MAINTAINWEIGHT
            else -> EnumGoal.LOSEWEIGHT
        }
        _onboardingUiState.update { it.copy(selectedGoal = goal, recommendedGoal = goal) }
    }

    private fun updateShouldShowWarningDialog() {
        val goal = _onboardingUiState.value.selectedGoal
        val bmiState = _bmiState.value

        val shouldShowDialogAboutOverWeight =
            (goal == EnumGoal.GAINWEIGHT || goal == EnumGoal.MAINTAINWEIGHT) && (bmiState == BMIState.OVERWEIGHT || bmiState == BMIState.FAT || bmiState == BMIState.OBESITY)
        if (shouldShowDialogAboutOverWeight) {
            _onboardingDialogUiState.update {
                it.copy(
                    shouldShowWarningDialog = true,
                    warningDialogMessage = "According to your input information, you are overweight. Our experts recommend that you should lose your weight!"
                )
            }
        }

        val shouldShowDialogAboutUnderWeight =
            (goal == EnumGoal.LOSEWEIGHT) && (bmiState == BMIState.UNDERWEIGHT)
        if (shouldShowDialogAboutUnderWeight) {
            _onboardingDialogUiState.update {
                it.copy(
                    shouldShowWarningDialog = true,
                    warningDialogMessage = "According to your input information, you are underweight. It would be dangerous if you try to lose more weight!"
                )
            }
        }
    }

    private fun updateRecommendedWeeklyGoal() {
        val goal = when (_onboardingUiState.value.selectedActivityLevel) {
            ActivityLevel.SEDENTARY -> 0.25f
            ActivityLevel.ACTIVE -> 1f
            else -> 0.5f
        }
        _onboardingUiState.update { it.copy(recommendedWeeklyGoal = goal) }
    }

    private fun updateWarningMessage() {
        val height = _onboardingUiState.value.heightPicker.toFloat() / 100
        val weight = _onboardingUiState.value.weightPicker.toFloat()

        val limitWeight = if (_onboardingUiState.value.selectedGoal == EnumGoal.GAINWEIGHT) {
            getWeightWithBMIAndHeight(24.9f, height)
        } else getWeightWithBMIAndHeight(18.6f, height)

        val newWeight = if (_onboardingUiState.value.selectedGoal == EnumGoal.GAINWEIGHT) {
            weight + _onboardingUiState.value.desiredWeightDifferencePicker
        } else weight - _onboardingUiState.value.desiredWeightDifferencePicker

        val warningMsg = if (newWeight > limitWeight) {
            "Based on your input information, your new weight will be $newWeight kg, and you will be overweight. The max weight you should pick is ${(newWeight - limitWeight).toInt()} kg"
        } else if (newWeight < limitWeight) {
            "Based on your input information, your new weight will be $newWeight kg, and you will be underweight. The max weight you should pick is ${(newWeight - limitWeight).toInt()} kg"
        } else null

        _onboardingUiState.update { it.copy(warningMessage = warningMsg) }
    }

    fun dismissWarningDialog() {
        _onboardingDialogUiState.update {
            it.copy(shouldShowWarningDialog = false)
        }
    }
}
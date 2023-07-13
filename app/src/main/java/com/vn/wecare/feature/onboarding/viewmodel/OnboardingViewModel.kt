package com.vn.wecare.feature.onboarding.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.usecase.UpdateWecareUserUsecase
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.usecase.DefineGoalBasedOnInputsUsecase
import com.vn.wecare.feature.home.goal.usecase.SaveGoalsToFirebaseUsecase
import com.vn.wecare.feature.home.goal.usecase.SetupGoalWeeklyRecordsWhenCreateNewGoalUsecase
import com.vn.wecare.feature.onboarding.ONBOARDING_PAGE_COUNT
import com.vn.wecare.feature.onboarding.model.BMIState
import com.vn.wecare.utils.WecareUserConstantValues
import com.vn.wecare.utils.WecareUserConstantValues.AGE_FIELD
import com.vn.wecare.utils.WecareUserConstantValues.BMI_NORMAL_RANGE
import com.vn.wecare.utils.WecareUserConstantValues.GENDER_FIELD
import com.vn.wecare.utils.WecareUserConstantValues.GOAL_FIELD
import com.vn.wecare.utils.WecareUserConstantValues.HEIGHT_FIELD
import com.vn.wecare.utils.WecareUserConstantValues.MIN_AGE
import com.vn.wecare.utils.WecareUserConstantValues.MIN_HEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.WEIGHT_FIELD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.pow

data class OnboardingUiState(
    val genderSelectionId: Int = 0,
    val agePicker: Int = MIN_AGE,
    val heightPicker: Int = MIN_HEIGHT,
    val weightPicker: Int = MIN_WEIGHT,
    val desiredWeightDifferencePicker: Int = 0,
    val estimatedWeeks: Int = 0,
    val selectedGoal: EnumGoal = EnumGoal.GETHEALTHIER,
    val updateInformationResult: Response<Boolean>? = null,
    val bmiState: BMIState = BMIState.UNDERWEIGHT
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
    private val updateWecareUserUsecase: UpdateWecareUserUsecase,
    private val accountService: AccountService,
    private val defineGoalBasedOnInputsUsecase: DefineGoalBasedOnInputsUsecase,
    private val saveGoalsToFirebaseUsecase: SaveGoalsToFirebaseUsecase,
    private val setupGoalWeeklyRecordsWhenCreateNewGoalUsecase: SetupGoalWeeklyRecordsWhenCreateNewGoalUsecase
) : ViewModel() {

    var currentIndex = mutableStateOf(0)

    fun onNextClick() {
        when (currentIndex.value) {
            0 -> updateUserGender()
            1 -> updateUserAge()
            2 -> updateUserHeight()
            3 -> {
                updateUserWeight()
                updateBMI()
                updateRecommendedGoal()
            }

            else -> {
                updateShouldShowWarningDialog(
                    _onboardingUiState.value.selectedGoal, _onboardingUiState.value.bmiState
                )
                updateShouldShowRecommendationDialog(
                    _onboardingUiState.value.selectedGoal, _onboardingUiState.value.bmiState
                )
                if (!_onboardingDialogUiState.value.shouldShowRecommendationDialog && !_onboardingDialogUiState.value.shouldShowWarningDialog) {
                    updateUserGoal()
                }
            }
        }
    }

    fun onWishToProcessAfterChoosingGoalClick() {
        updateUserGoal()
    }

    fun moveToNextOnboardingPage(
        moveToHomeScreen: () -> Unit, moveToPageAtIndex: () -> Unit
    ) {
        if (currentIndex.value < ONBOARDING_PAGE_COUNT - 1) {
            _onboardingUiState.update { it.copy(updateInformationResult = null) }
            currentIndex.value++
            moveToPageAtIndex()
        } else {
            moveToHomeScreen()
            clearOnboardingResult()
            currentIndex.value = 0
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
            it.copy(desiredWeightDifferencePicker = difference, estimatedWeeks = difference)
        }
    }

    fun onGoalSelect(goal: EnumGoal) {
        _onboardingUiState.update {
            it.copy(selectedGoal = goal)
        }
    }

    private fun clearOnboardingResult() {
        _onboardingUiState.update {
            it.copy(
                genderSelectionId = 0,
                weightPicker = 0,
                heightPicker = 0,
                desiredWeightDifferencePicker = 0,
                updateInformationResult = null,
                selectedGoal = EnumGoal.GAINMUSCLE
            )
        }
    }

    private fun updateUserGender() = viewModelScope.launch {
        _onboardingUiState.update { it.copy(updateInformationResult = Response.Loading) }
        updateWecareUserUsecase.updateWecareUserRoomDbWithId(
            accountService.currentUserId,
            GENDER_FIELD,
            getGenderWithId(_onboardingUiState.value.genderSelectionId)
        ).catch {
            _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
        }.collect { res ->
            if (res is Response.Success) {
                updateWecareUserUsecase.updateWecareUserFirestoreDbWithId(
                    accountService.currentUserId,
                    GENDER_FIELD,
                    getGenderWithId(_onboardingUiState.value.genderSelectionId)
                ).collect { res2 ->
                    if (res2 is Response.Success) {
                        _onboardingUiState.update {
                            it.copy(updateInformationResult = res2)
                        }
                    } else _onboardingUiState.update {
                        it.copy(updateInformationResult = Response.Error(null))
                    }
                }
            } else _onboardingUiState.update {
                it.copy(updateInformationResult = Response.Error(null))
            }
        }
    }

    private fun updateUserAge() = viewModelScope.launch {
        _onboardingUiState.update { it.copy(updateInformationResult = Response.Loading) }
        updateWecareUserUsecase.updateWecareUserRoomDbWithId(
            accountService.currentUserId, AGE_FIELD, _onboardingUiState.value.agePicker
        ).catch {
            _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
        }.collect { res ->
            if (res is Response.Success) {
                updateWecareUserUsecase.updateWecareUserFirestoreDbWithId(
                    accountService.currentUserId, AGE_FIELD, _onboardingUiState.value.agePicker
                ).collect { res2 ->
                    if (res2 is Response.Success) {
                        _onboardingUiState.update {
                            it.copy(updateInformationResult = res2)
                        }
                    } else _onboardingUiState.update {
                        it.copy(updateInformationResult = Response.Error(null))
                    }
                }
            } else _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
        }
    }

    private fun updateUserHeight() = viewModelScope.launch {
        _onboardingUiState.update { it.copy(updateInformationResult = Response.Loading) }
        updateWecareUserUsecase.updateWecareUserRoomDbWithId(
            accountService.currentUserId, HEIGHT_FIELD, _onboardingUiState.value.heightPicker
        ).catch {
            _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
        }.collect { res ->
            if (res is Response.Success) {
                updateWecareUserUsecase.updateWecareUserFirestoreDbWithId(
                    accountService.currentUserId,
                    HEIGHT_FIELD,
                    _onboardingUiState.value.heightPicker
                ).collect { res2 ->
                    if (res2 is Response.Success) {
                        _onboardingUiState.update {
                            it.copy(updateInformationResult = res2)
                        }
                    } else _onboardingUiState.update {
                        it.copy(updateInformationResult = Response.Error(null))
                    }
                }
            } else _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
        }
    }

    private fun updateUserWeight() = viewModelScope.launch {
        _onboardingUiState.update { it.copy(updateInformationResult = Response.Loading) }
        updateWecareUserUsecase.updateWecareUserRoomDbWithId(
            accountService.currentUserId, WEIGHT_FIELD, _onboardingUiState.value.weightPicker
        ).catch {
            _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
        }.collect { res ->
            if (res is Response.Success) {
                updateWecareUserUsecase.updateWecareUserFirestoreDbWithId(
                    accountService.currentUserId,
                    WEIGHT_FIELD,
                    _onboardingUiState.value.weightPicker
                ).collect { res2 ->
                    if (res2 is Response.Success) {
                        _onboardingUiState.update {
                            it.copy(updateInformationResult = res2)
                        }
                    } else _onboardingUiState.update {
                        it.copy(updateInformationResult = Response.Error(null))
                    }
                }
            } else _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
        }
    }

    private fun updateUserGoal() = viewModelScope.launch {
        _onboardingUiState.update { it.copy(updateInformationResult = Response.Loading) }
        updateWecareUserUsecase.updateWecareUserRoomDbWithId(
            accountService.currentUserId, GOAL_FIELD, _onboardingUiState.value.selectedGoal.value
        ).catch {
            _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
        }.collect { res ->
            if (res is Response.Success) {
                updateWecareUserUsecase.updateWecareUserFirestoreDbWithId(
                    accountService.currentUserId,
                    GOAL_FIELD,
                    _onboardingUiState.value.selectedGoal.value
                ).collect { res2 ->
                    if (res2 is Response.Success) {
                        saveGoalToFirebase()
                    } else _onboardingUiState.update {
                        it.copy(updateInformationResult = Response.Error(null))
                    }
                }
            } else _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
        }
    }

    private fun getGenderWithId(id: Int): Boolean {
        return id == 0
    }

    private fun saveGoalToFirebase() {
        val enumGoal = _onboardingUiState.value.selectedGoal
        _onboardingUiState.update { it.copy(updateInformationResult = Response.Loading) }
        val goal = defineGoalBasedOnInputsUsecase.getGoalFromInputs(
            goal = enumGoal,
            height = _onboardingUiState.value.heightPicker,
            weight = _onboardingUiState.value.weightPicker,
            age = _onboardingUiState.value.agePicker,
            gender = getGenderWithId(_onboardingUiState.value.genderSelectionId),
            weightDifference = if (enumGoal == EnumGoal.IMPROVEMOOD || enumGoal == EnumGoal.GETHEALTHIER) null else _onboardingUiState.value.desiredWeightDifferencePicker,
            timeToReachGoal = if (enumGoal == EnumGoal.IMPROVEMOOD || enumGoal == EnumGoal.GETHEALTHIER) null else _onboardingUiState.value.estimatedWeeks
        )
        viewModelScope.launch {
            saveGoalsToFirebaseUsecase.saveGoalsToFirebase(goal).collect { res ->
                if (res is Response.Success) {
                    setupGoalWeeklyRecordsWhenCreateNewGoalUsecase.setup(
                        goal.timeToReachGoalInWeek, goal.goalId
                    )
                    LatestGoalSingletonObject.updateInStance(goal)
                }
                _onboardingUiState.update {
                    it.copy(updateInformationResult = res)
                }
            }
        }
    }

    private fun updateBMI() {
        val bmi =
            (_onboardingUiState.value.weightPicker.toFloat() / (_onboardingUiState.value.heightPicker.toFloat() / 100).pow(
                2
            ))
        val bmiState = when (bmi) {
            in WecareUserConstantValues.BMI_UNDERWEIGHT_RANGE -> BMIState.UNDERWEIGHT
            in BMI_NORMAL_RANGE -> BMIState.NORMAL
            in WecareUserConstantValues.BMI_OVERWEIGHT_RANGE -> BMIState.OVERWEIGHT
            in WecareUserConstantValues.BMI_FAT_RANGE -> BMIState.FAT
            else -> BMIState.OBESITY
        }
        _onboardingUiState.update { it.copy(bmiState = bmiState) }
    }

    private fun updateRecommendedGoal() {
        if (_onboardingUiState.value.bmiState == BMIState.UNDERWEIGHT || _onboardingUiState.value.bmiState == BMIState.NORMAL) {
            _onboardingUiState.update { it.copy(selectedGoal = EnumGoal.GAINMUSCLE) }
        } else {
            _onboardingUiState.update { it.copy(selectedGoal = EnumGoal.LOSEWEIGHT) }
        }
    }

    private fun updateShouldShowWarningDialog(goal: EnumGoal, bmiState: BMIState) {
        val shouldShowDialogAboutOverWeight =
            (goal == EnumGoal.GAINMUSCLE) && (bmiState == BMIState.FAT || bmiState == BMIState.OBESITY)
        if (shouldShowDialogAboutOverWeight) {
            _onboardingDialogUiState.update {
                it.copy(
                    shouldShowWarningDialog = true,
                    warningDialogMessage = "You seem to be overweight, you should lose weight before gaining muscle"
                )
            }
        }

        val shouldShowDialogAboutUnderWeight =
            (goal == EnumGoal.LOSEWEIGHT) && (bmiState == BMIState.UNDERWEIGHT || bmiState == BMIState.NORMAL)
        if (shouldShowDialogAboutUnderWeight) {
            _onboardingDialogUiState.update {
                it.copy(
                    shouldShowWarningDialog = true,
                    warningDialogMessage = "You shouldn't try to lose more weight. You should pick the gain muscle to do more"
                )
            }
        }
    }

    private fun updateShouldShowRecommendationDialog(goal: EnumGoal, bmiState: BMIState) {
        val shouldShowGainWeightRecommendation =
            (goal == EnumGoal.GETHEALTHIER || goal == EnumGoal.IMPROVEMOOD) && (bmiState == BMIState.UNDERWEIGHT || bmiState == BMIState.NORMAL)
        if (shouldShowGainWeightRecommendation) {
            _onboardingDialogUiState.update {
                it.copy(
                    shouldShowRecommendationDialog = true,
                    recommendationDialogMessage = "Let us help you. You can do more by set your goal to Gain muscle!"
                )
            }
        }

        val shouldShowLoseWeightRecommendation =
            (goal == EnumGoal.GETHEALTHIER || goal == EnumGoal.IMPROVEMOOD) && (bmiState == BMIState.OVERWEIGHT || bmiState == BMIState.FAT || bmiState == BMIState.OBESITY)
        if (shouldShowLoseWeightRecommendation) {
            _onboardingDialogUiState.update {
                it.copy(
                    shouldShowRecommendationDialog = true,
                    recommendationDialogMessage = "You seem to a little bit overweight. You can do more by set your goal to Lose weight!"
                )
            }
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
}
package com.vn.wecare.feature.account.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.WecareUserSingletonObject
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.data.UserRepository
import com.vn.wecare.feature.account.data.model.WecareUser
import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.usecase.DefineGoalBasedOnInputsUsecase
import com.vn.wecare.feature.home.goal.usecase.SaveGoalsToFirebaseUsecase
import com.vn.wecare.feature.home.goal.usecase.SetupGoalWeeklyRecordsWhenCreateNewGoalUsecase
import com.vn.wecare.feature.home.goal.utils.getGoalEnumWithName
import com.vn.wecare.feature.onboarding.model.BMIState
import com.vn.wecare.feature.onboarding.viewmodel.OnboardingDialogUiState
import com.vn.wecare.utils.WecareUserConstantValues
import com.vn.wecare.utils.WecareUserConstantValues.MAX_AGE
import com.vn.wecare.utils.WecareUserConstantValues.MAX_HEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MAX_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_AGE
import com.vn.wecare.utils.WecareUserConstantValues.MIN_HEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_WEIGHT
import com.vn.wecare.utils.isValidUsername
import com.vn.wecare.utils.toIntSafely
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.pow

data class EditInfoUiState(
    val currentChosenGoal: EnumGoal = EnumGoal.GETHEALTHIER,
    val currentChosenGender: Int = 0,
    val isNewInfoDifferent: Boolean = false,
    val isUserNameValid: Boolean = true,
    val isHeightValid: Boolean = true,
    val isWeightValid: Boolean = true,
    val isAgeValid: Boolean = true,
    val updateInfoResult: Response<Boolean>? = null,
    val desiredWeightDifferencePicker: Int = 0,
    val estimatedWeeks: Int = 0,
    val isGoalExpired: Boolean = false
)

@HiltViewModel
class EditInfoViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val defineGoalBasedOnInputsUsecase: DefineGoalBasedOnInputsUsecase,
    private val saveGoalsToFirebaseUsecase: SaveGoalsToFirebaseUsecase,
    private val setupGoalWeeklyRecordsWhenCreateNewGoalUsecase: SetupGoalWeeklyRecordsWhenCreateNewGoalUsecase
) : ViewModel() {

    private val _editInfoUiState = MutableStateFlow(EditInfoUiState())
    val editInfoUiState = _editInfoUiState.asStateFlow()

    private val _onboardingDialogUiState = MutableStateFlow(OnboardingDialogUiState())
    val onboardingDialogUiState = _onboardingDialogUiState.asStateFlow()

    private val bmiState = MutableStateFlow(BMIState.NORMAL)

    fun initEditInfoScreenUiState() = viewModelScope.launch {
        WecareUserSingletonObject.getInstanceFlow().collect {
            onUserNameChange(it.userName)
            onHeightChange(it.height.toString())
            onWeightChange(it.weight.toString())
            onAgeChange(it.age.toString())
            updateCurrentGenderWhenInit(it.gender ?: true)
            updateCurrentGoalWhenInit(it.goal ?: EnumGoal.GETHEALTHIER.value)
        }
        checkIfGoalIsExpired()
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
        _editInfoUiState.update { it.copy(currentChosenGoal = goal) }
    }

    fun onGenderSelected(id: Int) {
        _editInfoUiState.update { it.copy(currentChosenGender = id) }
    }

    fun onPickDesiredWeightDifferenceScroll(difference: Int) {
        _editInfoUiState.update {
            it.copy(desiredWeightDifferencePicker = difference, estimatedWeeks = difference)
        }
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
            goal = _editInfoUiState.value.currentChosenGoal.value,
            gender = getGenderFromId(_editInfoUiState.value.currentChosenGender)
        )
        _editInfoUiState.update { it.copy(isNewInfoDifferent = newUser != oldUser) }
        return if (_editInfoUiState.value.isNewInfoDifferent) newUser else null
    }

    fun onSaveInfoClick(showToast: () -> Unit) {
        checkIfNewInfoIsDifferent()
        if (_editInfoUiState.value.isNewInfoDifferent) {
            checkIfUserNameValid()
            checkIfHeightValid()
            checkIfWeightValid()
            checkIfAgeValid()
            if (_editInfoUiState.value.isUserNameValid && _editInfoUiState.value.isHeightValid && _editInfoUiState.value.isWeightValid && _editInfoUiState.value.isAgeValid) {
                checkIfGoalIsAppropriate()
                if (!_onboardingDialogUiState.value.shouldShowRecommendationDialog && !_onboardingDialogUiState.value.shouldShowWarningDialog) {
                    saveNewUserInfoAndNewGoal()
                }
            }
        } else {
            showToast()
        }
    }

    fun saveNewUserInfoAndNewGoal() {
        val newUser = checkIfNewInfoIsDifferent()
        val enumGoal = _editInfoUiState.value.currentChosenGoal
        if (newUser != null) {
            _editInfoUiState.update { it.copy(updateInfoResult = Response.Loading) }
            viewModelScope.launch {
                userRepository.insertUserToFirebase(newUser)
                userRepository.insertUserToLocaldb(newUser)
                WecareUserSingletonObject.updateInstance(newUser)
                val goal = defineGoalBasedOnInputsUsecase.getGoalFromInputs(
                    goal = enumGoal,
                    height = height.toIntSafely(),
                    weight = weight.toIntSafely(),
                    age = age.toIntSafely(),
                    gender = getGenderFromId(_editInfoUiState.value.currentChosenGender),
                    weightDifference = if (enumGoal == EnumGoal.IMPROVEMOOD || enumGoal == EnumGoal.GETHEALTHIER) null else _editInfoUiState.value.desiredWeightDifferencePicker,
                    timeToReachGoal = if (enumGoal == EnumGoal.IMPROVEMOOD || enumGoal == EnumGoal.GETHEALTHIER) null else _editInfoUiState.value.estimatedWeeks
                )
                saveGoalsToFirebaseUsecase.saveGoalsToFirebase(goal).collect { res ->
                    if (res is Response.Success) {
                        setupGoalWeeklyRecordsWhenCreateNewGoalUsecase.invoke(
                            goal.timeToReachGoalInWeek, goal.goalId
                        )
                        LatestGoalSingletonObject.updateInStance(goal)
                    }
                    _editInfoUiState.update {
                        it.copy(updateInfoResult = res)
                    }
                }
                WecareCaloriesObject.updateUserCaloriesAmount()
            }
        }
    }

    fun resetUiState() {
        _editInfoUiState.update { EditInfoUiState() }
    }

    private fun updateCurrentGoalWhenInit(goal: String) {
        val goalEnum = getGoalEnumWithName(goal)
        _editInfoUiState.update { it.copy(currentChosenGoal = goalEnum) }
    }

    private fun updateCurrentGenderWhenInit(gender: Boolean) {
        val id = if (gender) 0 else 1
        _editInfoUiState.update { it.copy(currentChosenGender = id) }
    }

    private fun getGenderFromId(id: Int): Boolean {
        return (id == 1)
    }

    private fun checkIfUserNameValid() {
        _editInfoUiState.update { it.copy(isUserNameValid = userName.isValidUsername()) }
    }

    private fun checkIfHeightValid() {
        _editInfoUiState.update { it.copy(isHeightValid = height.toIntSafely() in MIN_HEIGHT..MAX_HEIGHT) }
    }

    private fun checkIfWeightValid() {
        _editInfoUiState.update { it.copy(isWeightValid = weight.toIntSafely() in MIN_WEIGHT..MAX_WEIGHT) }
    }

    private fun checkIfAgeValid() {
        _editInfoUiState.update { it.copy(isAgeValid = age.toIntSafely() in MIN_AGE..MAX_AGE) }
    }

    private fun checkIfGoalIsAppropriate() {
        val bmi = when ((weight.toFloat() / (height.toFloat() / 100).pow(2))) {
            in WecareUserConstantValues.BMI_UNDERWEIGHT_RANGE -> BMIState.UNDERWEIGHT
            in WecareUserConstantValues.BMI_NORMAL_RANGE -> BMIState.NORMAL
            in WecareUserConstantValues.BMI_OVERWEIGHT_RANGE -> BMIState.OVERWEIGHT
            in WecareUserConstantValues.BMI_FAT_RANGE -> BMIState.FAT
            else -> BMIState.OBESITY
        }
        bmiState.value = bmi

        val goal = _editInfoUiState.value.currentChosenGoal
        val shouldShowDialogAboutOverWeight =
            (goal == EnumGoal.GAINMUSCLE) && (bmiState.value == BMIState.FAT || bmiState.value == BMIState.OBESITY)
        if (shouldShowDialogAboutOverWeight) {
            _onboardingDialogUiState.update {
                it.copy(
                    shouldShowWarningDialog = true,
                    warningDialogMessage = "You seem to be overweight, you should lose weight before gaining muscle"
                )
            }
        }

        val shouldShowDialogAboutUnderWeight =
            (goal == EnumGoal.LOSEWEIGHT) && (bmiState.value == BMIState.UNDERWEIGHT || bmiState.value == BMIState.NORMAL)
        if (shouldShowDialogAboutUnderWeight) {
            _onboardingDialogUiState.update {
                it.copy(
                    shouldShowWarningDialog = true,
                    warningDialogMessage = "You shouldn't try to lose more weight. You should pick the gain muscle to do more"
                )
            }
        }

        val shouldShowGainWeightRecommendation =
            (goal == EnumGoal.GETHEALTHIER || goal == EnumGoal.IMPROVEMOOD) && (bmiState.value == BMIState.UNDERWEIGHT || bmiState.value == BMIState.NORMAL)
        if (shouldShowGainWeightRecommendation) {
            _onboardingDialogUiState.update {
                it.copy(
                    shouldShowRecommendationDialog = true,
                    recommendationDialogMessage = "Let us help you. You can do more by set your goal to Gain muscle!"
                )
            }
        }

        val shouldShowLoseWeightRecommendation =
            (goal == EnumGoal.GETHEALTHIER || goal == EnumGoal.IMPROVEMOOD) && (bmiState.value == BMIState.OVERWEIGHT || bmiState.value == BMIState.FAT || bmiState.value == BMIState.OBESITY)
        if (shouldShowLoseWeightRecommendation) {
            _onboardingDialogUiState.update {
                it.copy(
                    shouldShowRecommendationDialog = true,
                    recommendationDialogMessage = "You seem to a little bit overweight. You can do more by set your goal to Lose weight!"
                )
            }
        }
    }

    private fun checkIfGoalIsExpired() {
        val now = System.currentTimeMillis()
        val dateEndGoal = LatestGoalSingletonObject.getInStance().dateEndGoal
        _editInfoUiState.update {
            it.copy(
                isGoalExpired = now > dateEndGoal
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
}
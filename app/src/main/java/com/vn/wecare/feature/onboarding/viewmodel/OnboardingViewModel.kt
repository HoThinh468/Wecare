package com.vn.wecare.feature.onboarding.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.usecase.UpdateWecareUserUsecase
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.onboarding.ONBOARDING_PAGE_COUNT
import com.vn.wecare.utils.WecareUserConstantValues.AGE_FIELD
import com.vn.wecare.utils.WecareUserConstantValues.GAIN_MUSCLE
import com.vn.wecare.utils.WecareUserConstantValues.GENDER_FIELD
import com.vn.wecare.utils.WecareUserConstantValues.GET_HEALTHIER
import com.vn.wecare.utils.WecareUserConstantValues.GOAL_FIELD
import com.vn.wecare.utils.WecareUserConstantValues.HEIGHT_FIELD
import com.vn.wecare.utils.WecareUserConstantValues.IMPROVE_MOOD
import com.vn.wecare.utils.WecareUserConstantValues.LOOSE_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_AGE
import com.vn.wecare.utils.WecareUserConstantValues.MIN_HEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.WEIGHT_FIELD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingUiState(
    val genderSelectionId: Int = 0,
    val agePicker: Int = MIN_AGE,
    val heightPicker: Int = MIN_HEIGHT,
    val weightPicker: Int = MIN_WEIGHT,
    val goalSelectionId: Int = 0,
    val updateInformationResult: Response<Boolean>? = null
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val updateWecareUserUsecase: UpdateWecareUserUsecase,
    private val accountService: AccountService
) : ViewModel() {

    var currentIndex = mutableStateOf(0)

    fun onNextClick() {
        when (currentIndex.value) {
            0 -> updateUserGender()
//            1 -> updateUserAge()
//            2 -> updateUserHeight()
//            3 -> updateUserWeight()
//            else -> updateUserGoal()
            else -> {}
        }
    }

    fun moveToNextOnboardingPage(
        moveToHomeScreen: () -> Unit
    ) {
        if (currentIndex.value < ONBOARDING_PAGE_COUNT - 1) {
            currentIndex.value++
        } else {
            moveToHomeScreen()
        }
    }

    fun onPreviousClick() {
        if (currentIndex.value > 0) {
            currentIndex.value--
        }
    }

    private val _onboardingUiState = MutableStateFlow(OnboardingUiState())
    val onboardingUiState = _onboardingUiState.asStateFlow()

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

    fun onGoalSelect(id: Int) {
        _onboardingUiState.update {
            it.copy(goalSelectionId = id)
        }
    }

    private fun updateUserGender() = viewModelScope.launch {
        _onboardingUiState.update { it.copy(updateInformationResult = Response.Loading) }
        updateWecareUserUsecase.updateWecareUserRoomDbWithId(
            accountService.currentUserId,
            GENDER_FIELD,
            getGenderWithId(_onboardingUiState.value.genderSelectionId) as Boolean
        ).catch {
            _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
        }.collect { res ->
            if (res is Response.Success) {
                updateWecareUserUsecase.updateWecareUserFirestoreDbWithId(
                    accountService.currentUserId,
                    GENDER_FIELD,
                    getGenderWithId(_onboardingUiState.value.genderSelectionId) as Boolean
                ).collect { res2 ->
                    _onboardingUiState.update { it.copy(updateInformationResult = res2) }
                }
            } else _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
        }
    }

//    private fun updateUserAge() = viewModelScope.launch {
//        _onboardingUiState.update { it.copy(updateInformationResult = Response.Loading) }
//        updateWecareUserUsecase.updateWecareUserRoomDbWithId(
//            accountService.currentUserId, AGE_FIELD, _onboardingUiState.value.agePicker
//        ).catch {
//            _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
//        }.collect { res ->
//            if (res is Response.Success) {
//                updateWecareUserUsecase.updateWecareUserFirestoreDbWithId(
//                    accountService.currentUserId, AGE_FIELD, _onboardingUiState.value.agePicker
//                ).collect { res2 ->
//                    _onboardingUiState.update { it.copy(updateInformationResult = res2) }
//                }
//            } else _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
//        }
//    }
//
//    private fun updateUserHeight() = viewModelScope.launch {
//        _onboardingUiState.update { it.copy(updateInformationResult = Response.Loading) }
//        updateWecareUserUsecase.updateWecareUserRoomDbWithId(
//            accountService.currentUserId, HEIGHT_FIELD, _onboardingUiState.value.heightPicker
//        ).catch {
//            _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
//        }.collect { res ->
//            if (res is Response.Success) {
//                updateWecareUserUsecase.updateWecareUserFirestoreDbWithId(
//                    accountService.currentUserId,
//                    HEIGHT_FIELD,
//                    _onboardingUiState.value.heightPicker
//                ).collect { res2 ->
//                    _onboardingUiState.update { it.copy(updateInformationResult = res2) }
//                }
//            } else _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
//        }
//    }
//
//    private fun updateUserWeight() = viewModelScope.launch {
//        _onboardingUiState.update { it.copy(updateInformationResult = Response.Loading) }
//        updateWecareUserUsecase.updateWecareUserRoomDbWithId(
//            accountService.currentUserId, WEIGHT_FIELD, _onboardingUiState.value.weightPicker
//        ).catch {
//            _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
//        }.collect { res ->
//            if (res is Response.Success) {
//                updateWecareUserUsecase.updateWecareUserFirestoreDbWithId(
//                    accountService.currentUserId,
//                    WEIGHT_FIELD,
//                    _onboardingUiState.value.weightPicker
//                ).collect { res2 ->
//                    _onboardingUiState.update { it.copy(updateInformationResult = res2) }
//                }
//            } else _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
//        }
//    }
//
//    private fun updateUserGoal() = viewModelScope.launch {
//        _onboardingUiState.update { it.copy(updateInformationResult = Response.Loading) }
//        updateWecareUserUsecase.updateWecareUserRoomDbWithId(
//            accountService.currentUserId,
//            GOAL_FIELD,
//            getGoalWithId(_onboardingUiState.value.goalSelectionId)
//        ).catch {
//            _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
//        }.collect { res ->
//            if (res is Response.Success) {
//                updateWecareUserUsecase.updateWecareUserFirestoreDbWithId(
//                    accountService.currentUserId,
//                    GOAL_FIELD,
//                    getGoalWithId(_onboardingUiState.value.goalSelectionId)
//                ).collect { res2 ->
//                    _onboardingUiState.update { it.copy(updateInformationResult = res2) }
//                }
//            } else _onboardingUiState.update { it.copy(updateInformationResult = Response.Error(null)) }
//        }
//    }

    private fun getGenderWithId(id: Int): Boolean? {
        var res: Boolean? = null
        if (id == 0) res = true
        if (id == 1) res = false
        return res
    }

    private fun getGoalWithId(id: Int): String {
        return when (id) {
            0 -> GAIN_MUSCLE
            1 -> LOOSE_WEIGHT
            2 -> GET_HEALTHIER
            else -> IMPROVE_MOOD
        }
    }
}
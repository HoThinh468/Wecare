package com.vn.wecare.feature.account.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.WecareUserSingleton
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.data.UserRepository
import com.vn.wecare.feature.account.data.model.WecareUser
import com.vn.wecare.feature.account.view.EditInformationFragment
import com.vn.wecare.utils.WecareUserConstantValues
import com.vn.wecare.utils.WecareUserConstantValues.GAIN_MUSCLE
import com.vn.wecare.utils.WecareUserConstantValues.GET_HEALTHIER
import com.vn.wecare.utils.WecareUserConstantValues.LOOSE_WEIGHT
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

data class EditInfoUiState(
    val currentChosenGoal: Int = 0,
    val currentChosenGender: Int = 0,
    val isNewInfoDifferent: Boolean = false,
    val isUserNameValid: Boolean = true,
    val isHeightValid: Boolean = true,
    val isWeightValid: Boolean = true,
    val isAgeValid: Boolean = true,
    val updateInfoResult: Response<Boolean>? = null
)

@HiltViewModel
class EditInfoViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _editInfoUiState = MutableStateFlow(EditInfoUiState())
    val editInfoUiState = _editInfoUiState.asStateFlow()

    fun initEditInfoScreenUiState() = viewModelScope.launch {
        WecareUserSingleton.getInstanceFlow().collect {
            onUserNameChange(it.userName)
            onHeightChange(it.height.toString())
            onWeightChange(it.weight.toString())
            onAgeChange(it.age.toString())
            updateCurrentGoalIdWhenInit(it.goal ?: "")
            updateCurrentGenderWhenInit(it.gender ?: true)
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

    fun onGoalSelected(id: Int) {
        _editInfoUiState.update { it.copy(currentChosenGoal = id) }
    }

    fun onGenderSelected(id: Int) {
        _editInfoUiState.update { it.copy(currentChosenGender = id) }
    }

    fun checkIfNewInfoIsDifferent(): WecareUser? {
        val oldUser = WecareUserSingleton.getInstance()
        val newUser = WecareUser(
            userId = oldUser.userId,
            emailVerified = oldUser.emailVerified,
            email = oldUser.email,
            userName = userName,
            height = height.toIntSafely(),
            weight = weight.toIntSafely(),
            age = age.toIntSafely(),
            goal = getGoalFromId(_editInfoUiState.value.currentChosenGoal),
            gender = getGenderFromId(_editInfoUiState.value.currentChosenGender)
        )
        _editInfoUiState.update { it.copy(isNewInfoDifferent = newUser != oldUser) }
        return if (_editInfoUiState.value.isNewInfoDifferent) newUser else null
    }

    fun onSaveInfoClick(showToast: () -> Unit) {
        val newUser = checkIfNewInfoIsDifferent()
        if (_editInfoUiState.value.isNewInfoDifferent && newUser != null) {
            checkIfUserNameValid()
            checkIfHeightValid()
            checkIfWeightValid()
            checkIfAgeValid()
            if (_editInfoUiState.value.isUserNameValid && _editInfoUiState.value.isHeightValid && _editInfoUiState.value.isWeightValid && _editInfoUiState.value.isAgeValid) {
                _editInfoUiState.update { it.copy(updateInfoResult = Response.Loading) }
                viewModelScope.launch {
                    userRepository.insertUserToFirebase(newUser)
                    userRepository.insertUserToLocaldb(newUser)
                    WecareUserSingleton.updateInstance(newUser)
                    _editInfoUiState.update { it.copy(updateInfoResult = Response.Success(true)) }
                }
            }
        } else {
            showToast()
        }
    }

    fun resetUiState() {
        _editInfoUiState.update { EditInfoUiState() }
    }

    private fun updateCurrentGoalIdWhenInit(goal: String) {
        val id = when (goal) {
            GAIN_MUSCLE -> 1
            LOOSE_WEIGHT -> 2
            GET_HEALTHIER -> 3
            else -> 4
        }
        _editInfoUiState.update { it.copy(currentChosenGoal = id) }
    }

    private fun updateCurrentGenderWhenInit(gender: Boolean) {
        val id = if (gender) 1 else 2
        _editInfoUiState.update { it.copy(currentChosenGender = id) }
    }

    private fun getGoalFromId(id: Int): String {
        return when (id) {
            1 -> GAIN_MUSCLE
            2 -> LOOSE_WEIGHT
            3 -> GET_HEALTHIER
            else -> WecareUserConstantValues.IMPROVE_MOOD
        }
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
}
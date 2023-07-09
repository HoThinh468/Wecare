package com.vn.wecare.feature.home.bmi.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.WecareUserSingletonObject
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.ext.toIntFromString
import com.vn.wecare.feature.account.usecase.UpdateWecareUserUsecase
import com.vn.wecare.feature.home.bmi.data.BMIFAQs
import com.vn.wecare.feature.home.bmi.data.BMIFAQsModel
import com.vn.wecare.feature.home.bmi.ui.BMIFragment
import com.vn.wecare.feature.home.bmi.usecase.BMIUseCase
import com.vn.wecare.utils.WecareUserConstantValues.HEIGHT_FIELD
import com.vn.wecare.utils.WecareUserConstantValues.MAX_HEIGHT
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

data class BMIUiState(
    val weight: Int = 1,
    val height: Int = 1,
    val bmi: Float = 1f,
    val gender: Boolean = true,
    val updateInformationResult: Response<Boolean>? = null,
    val bmiProgress: Float = 0f
)

@HiltViewModel
class BMIViewModel @Inject constructor(
    private val updateWecareUserUsecase: UpdateWecareUserUsecase,
    private val bmiUseCase: BMIUseCase
) : ViewModel() {

    fun getFAQs(): List<BMIFAQsModel> = BMIFAQs.instance

    private val _uiState = MutableStateFlow(BMIUiState())
    val uiState = _uiState.asStateFlow()

    init {
        updateUserInformation()
    }

    fun resetUpdateInformation() {
        _uiState.update { it.copy(updateInformationResult = null) }
    }

    fun updateUserWeight(weight: String, isAddHistory: Boolean = false) {
        if (isWeightInputValid(weight)) {
            val user = WecareUserSingletonObject.getInstance()

            _uiState.update { it.copy(updateInformationResult = Response.Loading) }
            viewModelScope.launch {

                if(isAddHistory) {
                    bmiUseCase.addBMIHistory(
                        weight = weight.toIntFromString(),
                        height = null,
                        gender = user.gender ?: true,
                        age = user.age ?: 18
                    )
                }

                updateWecareUserUsecase.updateWecareUserRoomDbWithId(
                    user.userId, WEIGHT_FIELD, weight.toIntFromString()
                ).catch {
                    _uiState.update { it.copy(updateInformationResult = Response.Error(null)) }
                }.collect { res ->
                    if (res is Response.Success) {
                        updateWecareUserUsecase.updateWecareUserFirestoreDbWithId(
                            user.userId, WEIGHT_FIELD, weight.toIntFromString()
                        ).collect { res2 ->
                            if (res2 is Response.Success) {
                                _uiState.update {
                                    it.copy(updateInformationResult = res2)
                                }
                                WecareUserSingletonObject.updateInstance(user.copy(weight = weight.toIntFromString()))
                            } else _uiState.update {
                                it.copy(updateInformationResult = Response.Error(null))
                            }
                        }
                    } else _uiState.update { it.copy(updateInformationResult = Response.Error(null)) }
                }
            }
        } else {
            return
        }
    }

    private fun isWeightInputValid(weight: String): Boolean {
        if (weight.isEmpty()) return false
        return weight.toIntFromString() in 10..700
    }

    fun updateUserHeight(height: String, isAddHistory: Boolean = false) {
        if (isHeightInputValid(height)) {
            _uiState.update { it.copy(updateInformationResult = Response.Loading) }
            val user = WecareUserSingletonObject.getInstance()
            viewModelScope.launch {

                if(isAddHistory) {
                    bmiUseCase.addBMIHistory(
                        weight = null,
                        height = height.toIntFromString(),
                        gender = user.gender ?: true,
                        age = user.age ?: 18
                    )
                }

                updateWecareUserUsecase.updateWecareUserRoomDbWithId(
                    user.userId, HEIGHT_FIELD, height.toIntFromString()
                ).catch {
                    _uiState.update { it.copy(updateInformationResult = Response.Error(null)) }
                }.collect { res ->
                    if (res is Response.Success) {
                        updateWecareUserUsecase.updateWecareUserFirestoreDbWithId(
                            user.userId, HEIGHT_FIELD, height.toIntFromString()
                        ).collect { res2 ->
                            if (res2 is Response.Success) {
                                _uiState.update {
                                    it.copy(updateInformationResult = res2)
                                }
                                WecareUserSingletonObject.updateInstance(user.copy(height = height.toIntFromString()))
                            } else _uiState.update {
                                it.copy(updateInformationResult = Response.Error(null))
                            }
                        }
                    } else _uiState.update { it.copy(updateInformationResult = Response.Error(null)) }
                }
            }
        } else {
            return
        }
    }

    private fun isHeightInputValid(height: String): Boolean {
        if (height.isEmpty()) return false
        return height.toIntFromString() in MIN_HEIGHT..MAX_HEIGHT
    }

    private fun updateUserInformation() = viewModelScope.launch {
        WecareUserSingletonObject.getInstanceFlow().collect { user ->
            _uiState.update {
                it.copy(
                    weight = user.weight ?: MIN_WEIGHT,
                    height = user.height ?: MIN_HEIGHT,
                    bmi = calculateBMI(user.weight ?: MIN_WEIGHT, user.height ?: MIN_HEIGHT),
                    gender = user.gender ?: true,
                    bmiProgress = calculateBMI(
                        user.weight ?: MIN_WEIGHT, user.height ?: MIN_HEIGHT
                    ) / 50f
                )
            }
            Log.d(
                BMIFragment.bmiFlowTag,
                "${calculateBMI(user.weight ?: 30, user.height ?: 130) / 50f}"
            )
        }
    }

    private fun calculateBMI(weight: Int, height: Int): Float {
        return (weight.toFloat() / (height.toFloat() / 100).pow(2))
    }
}
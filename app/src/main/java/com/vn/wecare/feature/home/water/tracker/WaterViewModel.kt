package com.vn.wecare.feature.home.water.tracker

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class WaterUiState(
    val currentIndex: Int = 0,
    val targetAmount: Int = 2000,
    val desiredDrinkingAmountPageIndex: Int = 4,
    val progress: Float = 0f,
)

class WaterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WaterUiState())
    val uiState = _uiState.asStateFlow()

    private val waterOpacityList = listOf(50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600)

    fun getWaterOpacityNumber() = waterOpacityList.size

    fun getWaterDrinkingAmount() = waterOpacityList[_uiState.value.desiredDrinkingAmountPageIndex]

    fun onNextAmountClick() {
        if (_uiState.value.desiredDrinkingAmountPageIndex < waterOpacityList.size - 1) {
            _uiState.update {
                it.copy(desiredDrinkingAmountPageIndex = _uiState.value.desiredDrinkingAmountPageIndex + 1)
            }
        }
    }

    fun onPreviousAmountClick() {
        if (_uiState.value.desiredDrinkingAmountPageIndex > 0) {
            _uiState.update {
                it.copy(desiredDrinkingAmountPageIndex = _uiState.value.desiredDrinkingAmountPageIndex - 1)
            }
        }
    }

    fun onDrinkClick() {
        _uiState.update {
            it.copy(
                currentIndex = _uiState.value.currentIndex + waterOpacityList[_uiState.value.desiredDrinkingAmountPageIndex]
            )
        }
        if (_uiState.value.progress < 1f) {
            updateProgress()
        }
    }

    private fun updateProgress() {
        val progress = _uiState.value.currentIndex.toFloat() / _uiState.value.targetAmount.toFloat()
        _uiState.update { it.copy(progress = progress) }
    }
}
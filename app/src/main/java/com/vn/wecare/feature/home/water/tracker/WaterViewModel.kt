package com.vn.wecare.feature.home.water.tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.WecareUserSingleton
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.home.water.data.WaterRecordRepository
import com.vn.wecare.feature.home.water.data.model.WaterRecordEntity
import com.vn.wecare.utils.getDayId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

const val DEFAULT_WATER_TARGET_AMOUNT = 2000
const val DEFAULT_DRANK_AMOUNT = 0

data class WaterUiState(
    val currentIndex: Int = DEFAULT_DRANK_AMOUNT,
    val targetAmount: Int = DEFAULT_WATER_TARGET_AMOUNT,
    val desiredDrinkingAmountPageIndex: Int = 4,
    val progress: Float = 0f,
    val recordList: List<WaterRecordEntity> = emptyList()
)

@HiltViewModel
class WaterViewModel @Inject constructor(
    private val repository: WaterRecordRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(WaterUiState())
    val uiState = _uiState.asStateFlow()

    private val waterOpacityList = listOf(50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600)

    fun initWaterView() {
        getWaterTarget()
        fetchRecordList()
    }

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

    fun onDrinkClick() = viewModelScope.launch {
        val currentTime = Calendar.getInstance()
        val newRecord = WaterRecordEntity(
            recordId = generateRecordId(currentTime),
            amount = getWaterDrinkingAmount(),
            dateTime = currentTime,
            userId = WecareUserSingleton.getInstance().userId,
            dayId = getDayId(
                currentTime.get(Calendar.DAY_OF_MONTH),
                currentTime.get(Calendar.MONTH),
                currentTime.get(Calendar.YEAR)
            ),
            currentTarget = _uiState.value.targetAmount
        )
        repository.insertRecord(newRecord)
        fetchRecordList()
    }

    fun onDeleteRecordClick(record: WaterRecordEntity) = viewModelScope.launch(ioDispatcher) {
        repository.deleteRecord(record)
        fetchRecordList()
    }

    fun updateRecordAmountWithId(amount: Int, record: WaterRecordEntity) =
        viewModelScope.launch(ioDispatcher) {
            repository.updateRecordAmountWithId(amount, record)
            fetchRecordList()
        }

    private fun fetchRecordList() = viewModelScope.launch(ioDispatcher) {
        val currentTime = Calendar.getInstance()
        repository.getRecordsWithDayId(
            currentTime.get(Calendar.DAY_OF_MONTH),
            currentTime.get(Calendar.MONTH),
            currentTime.get(Calendar.YEAR)
        ).collect { res ->
            if (res is Response.Success && res.data != null) {
                _uiState.update {
                    it.copy(
                        recordList = res.data
                    )
                }
                calculateTotalAmountDrinkToday()
                updateProgress()
            }
        }
    }

    private fun calculateTotalAmountDrinkToday() {
        _uiState.update { it.copy(currentIndex = 0) }
        repeat(_uiState.value.recordList.size) { index ->
            _uiState.update {
                it.copy(currentIndex = it.currentIndex + _uiState.value.recordList[index].amount)
            }
        }
    }

    private fun getWaterTarget() = viewModelScope.launch {
        WecareUserSingleton.getInstanceFlow().collect { user ->
            _uiState.update {
                it.copy(targetAmount = (user.weight?.times(30) ?: 2000f).toInt())
            }
        }
    }

    private fun updateProgress() {
        if (_uiState.value.progress < 1f) {
            val progress =
                _uiState.value.currentIndex.toFloat() / _uiState.value.targetAmount.toFloat()
            _uiState.update { it.copy(progress = progress) }
        } else _uiState.update { it.copy(progress = 1f) }
    }

    private fun generateRecordId(time: Calendar): String {
        val year = time.get(Calendar.YEAR)
        val month = time.get(Calendar.MONTH)
        val day = time.get(Calendar.DAY_OF_MONTH)
        val hour = time.get(Calendar.HOUR_OF_DAY)
        val minute = time.get(Calendar.MINUTE)
        val second = time.get(Calendar.SECOND)
        return "${year}_${month}_${day}_${hour}_${minute}_${second}"
    }
}
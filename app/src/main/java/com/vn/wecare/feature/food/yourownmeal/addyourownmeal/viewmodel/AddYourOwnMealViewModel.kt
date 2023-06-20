package com.vn.wecare.feature.food.yourownmeal.addyourownmeal.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.MealsRepository
import com.vn.wecare.feature.food.data.model.Meal
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.usecase.CalculateNutrientsIndexUsecase
import com.vn.wecare.feature.food.usecase.InsertMealRecordUsecase
import com.vn.wecare.utils.isLetters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddMealUiState(
    val protein: Int = 0,
    val fat: Int = 0,
    val carbs: Int = 0,
    val currentChosenCategory: MealTypeKey = MealTypeKey.BREAKFAST,
    val imageUri: Uri? = null,
    val isNameValid: Boolean = true,
    val isCaloriesValid: Boolean = true,
    val isImageUploaded: Boolean = true,
    val saveMealToFirebaseResponse: Response<Boolean>? = null
)

@HiltViewModel
class AddYourOwnMealViewModel @Inject constructor(
    private val calculateNutrientsIndexUsecase: CalculateNutrientsIndexUsecase,
    private val mealsRepository: MealsRepository,
) : ViewModel() {

    private val _addMealUiState = MutableStateFlow(AddMealUiState())
    val addMealUiState = _addMealUiState.asStateFlow()

    var mealName by mutableStateOf("")
    fun onNameChange(newName: String) {
        mealName = newName
    }

    fun clearMealName() {
        mealName = ""
    }

    var calories by mutableStateOf("0")
    fun onCaloriesChange(newCalories: String) {
        calories = newCalories
        if (calories.isNotBlank()) {
            val intCalo = calories.toInt()
            if (intCalo <= 1000) {
                _addMealUiState.update {
                    it.copy(
                        protein = calculateNutrientsIndexUsecase.getProteinIndexInGram(intCalo),
                        fat = calculateNutrientsIndexUsecase.getFatIndexInGram(intCalo),
                        carbs = calculateNutrientsIndexUsecase.getCarbIndexInGram(intCalo)
                    )
                }
            }
        }
    }

    fun clearCalories() {
        calories = ""
    }

    fun updateChosenCategory(key: MealTypeKey) {
        _addMealUiState.update {
            it.copy(currentChosenCategory = key)
        }
    }

    fun updateImageUri(uri: Uri?) {
        _addMealUiState.update { it.copy(imageUri = uri) }
    }

    fun onSaveMealClick() = viewModelScope.launch {
        checkIfNameIsError()
        checkIfCaloriesIsError()
        checkIfImageIsUploaded()
        if (_addMealUiState.value.isNameValid && _addMealUiState.value.isCaloriesValid && _addMealUiState.value.isImageUploaded) {
            val timeStamp = System.currentTimeMillis()
            _addMealUiState.update { it.copy(saveMealToFirebaseResponse = Response.Loading) }
            val result = combine(
                mealsRepository.insertYourOwnMealToFirebase(createMealWithTimeStamp(timeStamp)),
                mealsRepository.insertMealImageToFirebaseStorage(
                    timeStamp,
                    _addMealUiState.value.imageUri!!,
                    _addMealUiState.value.currentChosenCategory.value
                )
            ) { res1, res2 ->
                if (res1 is Response.Success && res2 is Response.Success) {
                    Response.Success(true)
                } else Response.Error(null)
            }
            result.collect { res ->
                _addMealUiState.update { it.copy(saveMealToFirebaseResponse = res) }
            }
        }
    }

    private fun checkIfNameIsError() {
        _addMealUiState.update {
            it.copy(
                isNameValid = mealName.isNotBlank() && mealName.isLetters()
            )
        }
    }

    private fun checkIfCaloriesIsError() {
        _addMealUiState.update {
            it.copy(
                isCaloriesValid = calories.toInt() in 1..9_000
            )
        }
    }

    private fun checkIfImageIsUploaded() {
        _addMealUiState.update {
            it.copy(isImageUploaded = _addMealUiState.value.imageUri != null)
        }
    }

    private fun createMealWithTimeStamp(timeStamp: Long): Meal = Meal(
        id = timeStamp,
        name = mealName,
        category = _addMealUiState.value.currentChosenCategory.value,
        calories = calories.toInt(),
        protein = _addMealUiState.value.protein,
        fat = _addMealUiState.value.fat,
        carbs = _addMealUiState.value.carbs
    )

    fun resetUiState() {
        _addMealUiState.update { AddMealUiState() }
        mealName = ""
        calories = "0"
    }
}
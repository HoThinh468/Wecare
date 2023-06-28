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
import com.vn.wecare.utils.isLetters
import com.vn.wecare.utils.toIntSafely
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddMealUiState(
    val calories: Int = 0,
    val currentChosenCategory: MealTypeKey = MealTypeKey.BREAKFAST,
    val imageUri: Uri? = null,
    val isNameValid: Boolean = true,
    val areNutrientsValid: Boolean = true,
    val isImageUploaded: Boolean = true,
    val saveMealToFirebaseResponse: Response<Boolean>? = null,
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

    var fat by mutableStateOf("0")
    fun onFatChange(newFat: String) {
        fat = newFat
        updateCaloriesValue()
    }

    var protein by mutableStateOf("0")
    fun onProteinChange(newProtein: String) {
        protein = newProtein
        updateCaloriesValue()
    }

    var carbs by mutableStateOf("0")
    fun onCarbsChange(newCarbs: String) {
        carbs = newCarbs
        updateCaloriesValue()
    }

    private fun updateCaloriesValue() {
        val calories = calculateNutrientsIndexUsecase.getCaloriesFromNutrients(
            protein.toIntSafely(), fat.toIntSafely(), carbs.toIntSafely()
        )
        _addMealUiState.update { it.copy(calories = calories) }
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
        checkIfImageIsUploaded()
        checkIfNutrientsAreValid()
        if (_addMealUiState.value.isNameValid && _addMealUiState.value.isImageUploaded && _addMealUiState.value.areNutrientsValid) {
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

    private fun checkIfImageIsUploaded() {
        _addMealUiState.update {
            it.copy(isImageUploaded = _addMealUiState.value.imageUri != null)
        }
    }

    private fun checkIfNutrientsAreValid() {
        val isProteinValid = protein.toIntSafely() in 1..1000
        val isFatValid = fat.toIntSafely() in 1..1000
        val isCarbsValid = carbs.toIntSafely() in 1..1000
        _addMealUiState.update { it.copy(areNutrientsValid = isProteinValid && isCarbsValid && isFatValid) }
    }

    private fun createMealWithTimeStamp(timeStamp: Long): Meal = Meal(
        id = timeStamp,
        name = mealName,
        category = _addMealUiState.value.currentChosenCategory.value,
        calories = _addMealUiState.value.calories,
        protein = protein.toIntSafely(),
        fat = fat.toIntSafely(),
        carbs = carbs.toIntSafely()
    )

    fun resetUiState() {
        _addMealUiState.update { AddMealUiState() }
        mealName = ""
        fat = "0"
    }
}
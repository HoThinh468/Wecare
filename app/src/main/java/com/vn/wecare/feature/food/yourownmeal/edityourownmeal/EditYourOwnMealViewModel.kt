package com.vn.wecare.feature.food.yourownmeal.edityourownmeal

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

data class EditMealUiState(
    val calories: Int = 0,
    val currentChosenCategory: MealTypeKey = MealTypeKey.BREAKFAST,
    val imageUri: Uri? = null,
    val isNameValid: Boolean = true,
    val isImageUploaded: Boolean = true,
    val saveMealToFirebaseResponse: Response<Boolean>? = null,
    val deleteMealResponse: Response<Boolean>? = null,
    val currentMeal: Meal = Meal(),
    val areNutrientsValid: Boolean = true,
)

@HiltViewModel
class EditYourOwnMealViewModel @Inject constructor(
    private val calculateNutrientsIndexUsecase: CalculateNutrientsIndexUsecase,
    private val mealsRepository: MealsRepository,
) : ViewModel() {

    private val _editMealUiState = MutableStateFlow(EditMealUiState())
    val editMealUiState = _editMealUiState.asStateFlow()

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
        _editMealUiState.update { it.copy(calories = calories) }
    }

    fun updateChosenCategory(key: MealTypeKey) {
        _editMealUiState.update {
            it.copy(currentChosenCategory = key)
        }
    }

    fun updateImageUri(uri: Uri?) {
        _editMealUiState.update { it.copy(imageUri = uri) }
    }

    fun initEditMealUiState(meal: Meal) {
        mealName = meal.name
        protein = meal.protein.toString()
        fat = meal.fat.toString()
        carbs = meal.carbs.toString()
        _editMealUiState.update {
            it.copy(
                calories = meal.calories,
                currentChosenCategory = getMealTypeKeyByKey(meal.category),
                imageUri = Uri.parse(meal.imgUrl),
                currentMeal = meal
            )
        }
    }

    fun onSaveMealClick() = viewModelScope.launch {
        checkIfNameIsError()
        checkIfImageIsUploaded()
        checkIfNutrientsAreValid()
        if (_editMealUiState.value.isNameValid && _editMealUiState.value.areNutrientsValid && _editMealUiState.value.isImageUploaded) {
            _editMealUiState.update { it.copy(saveMealToFirebaseResponse = Response.Loading) }
            val result = combine(
                mealsRepository.insertYourOwnMealToFirebase(getNewUpdatedMeal()),
                mealsRepository.insertMealImageToFirebaseStorage(
                    _editMealUiState.value.currentMeal.id,
                    _editMealUiState.value.imageUri!!,
                    _editMealUiState.value.currentChosenCategory.value
                ),
                mealsRepository.deleteMealFromFirebase(_editMealUiState.value.currentMeal)
            ) { res1, res2, res3 ->
                if (res1 is Response.Success && res2 is Response.Success && res3 is Response.Success) {
                    Response.Success(true)
                } else Response.Error(null)
            }
            result.collect { res ->
                _editMealUiState.update { it.copy(saveMealToFirebaseResponse = res) }
            }
        }
    }

    fun onDeleteMealClick(meal: Meal) = viewModelScope.launch {
        _editMealUiState.update { it.copy(deleteMealResponse = Response.Loading) }
        val result = combine(
            mealsRepository.deleteMealFromFirebase(meal),
            mealsRepository.deleteMealImageFromFirebase(meal.id)
        ) { res1, res2 ->
            if (res1 is Response.Success && res2 is Response.Success) {
                Response.Success(true)
            } else {
                val e = Exception("Fail to delete meal from firebase")
                Response.Error(e)
            }
        }
        result.collect { res ->
            _editMealUiState.update { it.copy(deleteMealResponse = res) }
        }
    }

    private fun checkIfNameIsError() {
        _editMealUiState.update {
            it.copy(
                isNameValid = mealName.isNotBlank() && mealName.isLetters()
            )
        }
    }

    private fun checkIfImageIsUploaded() {
        _editMealUiState.update {
            it.copy(isImageUploaded = _editMealUiState.value.imageUri != null)
        }
    }

    private fun getNewUpdatedMeal(): Meal = Meal(
        id = _editMealUiState.value.currentMeal.id,
        name = mealName,
        category = _editMealUiState.value.currentChosenCategory.value,
        protein = protein.toIntSafely(),
        fat = fat.toIntSafely(),
        carbs = carbs.toIntSafely(),
        imgUrl = _editMealUiState.value.imageUri.toString(),
        calories = _editMealUiState.value.calories
    )

    private fun getMealTypeKeyByKey(key: String): MealTypeKey = when (key) {
        "breakfast" -> MealTypeKey.BREAKFAST
        "lunch" -> MealTypeKey.LUNCH
        "snack" -> MealTypeKey.SNACK
        else -> MealTypeKey.DINNER
    }

    private fun checkIfNutrientsAreValid() {
        val isProteinValid = protein.toIntSafely() in 1..1000
        val isFatValid = fat.toIntSafely() in 1..1000
        val isCarbsValid = carbs.toIntSafely() in 1..1000
        _editMealUiState.update { it.copy(areNutrientsValid = isProteinValid && isCarbsValid && isFatValid) }
    }

    fun resetUiState() {
        _editMealUiState.update {
            it.copy(saveMealToFirebaseResponse = null, deleteMealResponse = null)
        }
    }
}
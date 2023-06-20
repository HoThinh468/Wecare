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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditMealUiState(
    val protein: Int = 0,
    val fat: Int = 0,
    val carbs: Int = 0,
    val currentChosenCategory: MealTypeKey = MealTypeKey.BREAKFAST,
    val imageUri: Uri? = null,
    val isNameValid: Boolean = true,
    val isCaloriesValid: Boolean = true,
    val isImageUploaded: Boolean = true,
    val saveMealToFirebaseResponse: Response<Boolean>? = null,
    val deleteMealResponse: Response<Boolean>? = null,
    val currentMeal: Meal = Meal()
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

    var calories by mutableStateOf("0")
    fun onCaloriesChange(newCalories: String) {
        calories = newCalories
        if (calories.isNotBlank()) {
            val intCalo = calories.toInt()
            if (intCalo <= 1000) {
                _editMealUiState.update {
                    it.copy(
                        protein = calculateNutrientsIndexUsecase.getProteinIndexInGram(intCalo),
                        fat = calculateNutrientsIndexUsecase.getFatIndexInGram(intCalo),
                        carbs = calculateNutrientsIndexUsecase.getCarbIndexInGram(intCalo),
                    )
                }
            }
        }
    }

    fun clearCalories() {
        calories = ""
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
        calories = meal.calories.toString()
        _editMealUiState.update {
            it.copy(
                protein = meal.protein,
                fat = meal.fat,
                carbs = meal.carbs,
                currentChosenCategory = getMealTypeKeyByKey(meal.category),
                imageUri = Uri.parse(meal.imgUrl),
                currentMeal = meal
            )
        }
    }

    /**
     * Work well with the same category
     */
//    fun onSaveMealClick() = viewModelScope.launch {
//        checkIfNameIsError()
//        checkIfCaloriesIsError()
//        checkIfImageIsUploaded()
//        if (_editMealUiState.value.isNameValid && _editMealUiState.value.isCaloriesValid && _editMealUiState.value.isImageUploaded) {
//            _editMealUiState.update { it.copy(saveMealToFirebaseResponse = Response.Loading) }
//            if (_editMealUiState.value.currentMeal.category != _editMealUiState.value.currentChosenCategory.value) {
//                mealsRepository.deleteMealFromFirebase(_editMealUiState.value.currentMeal)
//            }
//            val result = combine(
//                mealsRepository.insertYourOwnMealToFirebase(getNewUpdatedMeal()),
//                mealsRepository.insertMealImageToFirebaseStorage(
//                    _editMealUiState.value.currentMeal.id,
//                    _editMealUiState.value.imageUri!!,
//                    _editMealUiState.value.currentChosenCategory.value
//                )
//            ) { res1, res2 ->
//                if (res1 is Response.Success && res2 is Response.Success) {
//                    Response.Success(true)
//                } else Response.Error(null)
//            }
//            result.collect { res ->
//                _editMealUiState.update { it.copy(saveMealToFirebaseResponse = res) }
//            }
//        }
//    }

    fun onSaveMealClick() = viewModelScope.launch {
        checkIfNameIsError()
        checkIfCaloriesIsError()
        checkIfImageIsUploaded()
        if (_editMealUiState.value.isNameValid && _editMealUiState.value.isCaloriesValid && _editMealUiState.value.isImageUploaded) {
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

    private fun checkIfCaloriesIsError() {
        _editMealUiState.update {
            it.copy(
                isCaloriesValid = calories.toInt() in 1..9_000
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
        calories = calories.toInt(),
        protein = _editMealUiState.value.protein,
        fat = _editMealUiState.value.fat,
        carbs = _editMealUiState.value.carbs,
        imgUrl = _editMealUiState.value.imageUri.toString()
    )

    private fun getMealTypeKeyByKey(key: String): MealTypeKey = when (key) {
        "breakfast" -> MealTypeKey.BREAKFAST
        "lunch" -> MealTypeKey.LUNCH
        "snack" -> MealTypeKey.SNACK
        else -> MealTypeKey.DINNER
    }

    fun resetUiState() {
        _editMealUiState.update {
            it.copy(saveMealToFirebaseResponse = null, deleteMealResponse = null)
        }
    }
}
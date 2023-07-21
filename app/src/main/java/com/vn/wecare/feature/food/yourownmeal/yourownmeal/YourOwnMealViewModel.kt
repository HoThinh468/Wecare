package com.vn.wecare.feature.food.yourownmeal.yourownmeal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.repository.MealsRepository
import com.vn.wecare.feature.food.data.model.Meal
import com.vn.wecare.feature.food.data.model.MealByNutrients
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.data.model.toMealRecipe
import com.vn.wecare.feature.food.usecase.InsertMealRecordUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class YourOwnMealUiState(
    val mealList: List<Meal> = emptyList(),
    val currentCategory: MealTypeKey? = null,
    val getMealResult: Response<Boolean>? = null,
    val addMealRecordResponse: Response<Boolean>? = null
)

@HiltViewModel
class YourOwnMealViewModel @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val insertMealRecordUsecase: InsertMealRecordUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow(YourOwnMealUiState())
    val uiState = _uiState.asStateFlow()

    var mealName by mutableStateOf("")
    fun onNameChange(newName: String) {
        mealName = newName
    }

    fun clearMealName() {
        mealName = ""
    }

    fun onMealCategoryChosen(mealTypeKey: MealTypeKey) {
        if (mealTypeKey != _uiState.value.currentCategory) {
            _uiState.update { it.copy(currentCategory = mealTypeKey) }
            getMealsWithCategory(mealTypeKey.value)
        }
    }

    fun resetUIState() {
        _uiState.update { YourOwnMealUiState() }
    }

    fun initMealsOfAllTypeList() {
        insertMealRecordUsecase.getMealsOfAllTypeList()
    }

    fun resetMealsOfAllTypeList() {
        insertMealRecordUsecase.resetMealListOfAllType()
    }

    fun insertMealRecord(mealTypeKey: MealTypeKey, meal: Meal) = viewModelScope.launch {
        _uiState.update { it.copy(addMealRecordResponse = Response.Loading) }
        insertMealRecordUsecase.insertMealRecord(Calendar.getInstance(), mealTypeKey, meal.toMealRecipe())
            .collect { res ->
                _uiState.update { it.copy(addMealRecordResponse = res) }
            }
    }

    private fun getMealsWithCategory(key: String) = viewModelScope.launch {
        _uiState.update { it.copy(getMealResult = Response.Loading) }
        mealsRepository.getYourOwnMealWithCategory(key).collect { res ->
            if (res is Response.Success) {
                _uiState.update {
                    it.copy(
                        mealList = res.data, getMealResult = Response.Success(true)
                    )
                }
            } else {
                _uiState.update { it.copy(getMealResult = Response.Error(null)) }
            }
        }
    }
}
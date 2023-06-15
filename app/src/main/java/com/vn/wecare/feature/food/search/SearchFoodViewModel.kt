package com.vn.wecare.feature.food.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.addmeal.ui.DEFAULT_MEAL_BY_NUTRIENT
import com.vn.wecare.feature.food.data.MealsRepository
import com.vn.wecare.feature.food.data.model.MealByName
import com.vn.wecare.feature.food.data.model.MealByNutrients
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.usecase.InsertMealRecordUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class SearchMealUiSate(
    val loadDataResult: Response<Boolean>? = null,
    val currentChosenMeal: MealByNutrients = DEFAULT_MEAL_BY_NUTRIENT,
    val addMealRecordResult: Response<Boolean>? = null,
    val currentChosenMealType: MealTypeKey = MealTypeKey.BREAKFAST
)

@HiltViewModel
class SearchFoodViewModel @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val insertMealRecordUsecase: InsertMealRecordUsecase
) : ViewModel() {

    private val _searchMealUiState = MutableStateFlow(SearchMealUiSate())
    val searchMealUiSate = _searchMealUiState.asStateFlow()

    private val _inputQuery = MutableStateFlow("")
    val inputQuery = _inputQuery.asStateFlow()
    fun onQueryChange(newQuery: String) {
        _inputQuery.update { newQuery }
    }

    private val _searchResults = MutableStateFlow(emptyList<MealByName>())
    val searchResults = _searchResults.asStateFlow()

    fun clearQuery() {
        _inputQuery.update { "" }
    }

    fun initMealsOfAllTypeList() {
        insertMealRecordUsecase.getMealsOfAllTypeList()
    }

    fun resetMealsOfAllTypeList() {
        insertMealRecordUsecase.resetMealListOfAllType()
    }

    fun resetAddMealRecordResult() {
        _searchMealUiState.update { it.copy(addMealRecordResult = null) }
    }

    fun insertMealRecord(mealTypeKey: MealTypeKey, meal: MealByNutrients) = viewModelScope.launch {
        _searchMealUiState.update { it.copy(addMealRecordResult = Response.Loading) }
        insertMealRecordUsecase.insertMealRecord(Calendar.getInstance(), mealTypeKey, meal)
            .collect { res ->
                _searchMealUiState.update { it.copy(addMealRecordResult = res) }
            }
    }

    fun onSearchForQuery() = viewModelScope.launch {
        if (inputQuery.value.isNotEmpty()) {
            _searchMealUiState.update { it.copy(loadDataResult = Response.Loading) }
            mealsRepository.getMealByName(inputQuery.value).collect { res ->
                if (res is Response.Success) {
                    _searchMealUiState.update { ui ->
                        ui.copy(
                            loadDataResult = Response.Success(true)
                        )
                    }
                    _searchResults.update { res.data.results }
                } else {
                    _searchMealUiState.update {
                        it.copy(loadDataResult = Response.Error(null))
                    }
                }
            }
        }
    }

    fun updateCurrentChosenMeal(meal: MealByNutrients) {
        _searchMealUiState.update { it.copy(currentChosenMeal = meal) }
    }

    fun updateChosenMealTypeKey(key: MealTypeKey) {
        _searchMealUiState.update { it.copy(currentChosenMealType = key) }
    }
}
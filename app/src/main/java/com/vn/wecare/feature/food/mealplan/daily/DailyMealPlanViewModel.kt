package com.vn.wecare.feature.food.mealplan.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.data.model.Recipe.RecipeInformation
import com.vn.wecare.feature.food.data.model.Recipe.toMealRecipe
import com.vn.wecare.feature.food.data.model.mealplan.MealPlan
import com.vn.wecare.feature.food.data.repository.MealPlanRepository
import com.vn.wecare.feature.food.usecase.InsertMealRecordUsecase
import com.vn.wecare.utils.getCurrentDayId
import com.vn.wecare.utils.getDayId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject

data class DailyMealPlanUiState(
    val getMealPlanResponse: Response<Boolean>? = null,
    val addMealResponse: Response<Boolean>? = null,
    val dailyMealPlan: List<RecipeInformation> = emptyList(),
    val totalCalories: Int = 0,
    val totalProtein: Int = 0,
    val totalFat: Int = 0,
    val totalCarbs: Int = 0,
    val currentChosenRecipe: RecipeInformation = RecipeInformation(),
    val currentChosenMealType: MealTypeKey = MealTypeKey.BREAKFAST
)

@HiltViewModel
class DailyMealPlanViewModel @Inject constructor(
    private val repo: MealPlanRepository,
    private val insertMealRecordUsecase: InsertMealRecordUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyMealPlanUiState())
    val uiState = _uiState.asStateFlow()

    fun initUiState(localDate: LocalDate) {
        val now = LocalDate.now()
        if (localDate.dayOfMonth == now.dayOfMonth && localDate.month == now.month && localDate.year == now.year) {
            setupDailyMealPlanUI(localDate)
        } else {
            viewModelScope.launch {
                val dayId = getDayId(localDate.dayOfMonth, localDate.monthValue, localDate.year)
                _uiState.update { it.copy(getMealPlanResponse = Response.Loading) }
                repo.getMealPlanFromFirestore(dayId).collect { res ->
                    if (res is Response.Success && res.data != null) {
                        _uiState.update {
                            it.copy(
                                dailyMealPlan = res.data,
                                getMealPlanResponse = Response.Success(true)
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                dailyMealPlan = emptyList(),
                                getMealPlanResponse = Response.Success(true)
                            )
                        }
                    }
                }
            }
        }
    }

    fun onRecipeClick(recipe: RecipeInformation, mealTypeKey: MealTypeKey) {
        _uiState.update {
            it.copy(
                currentChosenRecipe = recipe, currentChosenMealType = mealTypeKey
            )
        }
    }

    fun onAddRecipeClick(recipe: RecipeInformation, mealTypeKey: MealTypeKey) =
        viewModelScope.launch {
            _uiState.update { it.copy(addMealResponse = Response.Loading) }
            insertMealRecordUsecase.insertMealRecord(
                Calendar.getInstance(), mealTypeKey, recipe.toMealRecipe()
            ).collect { res ->
                _uiState.update { it.copy(addMealResponse = res) }
            }
        }

    private fun setupDailyMealPlanUI(localDate: LocalDate) = viewModelScope.launch {
        val dayId = getDayId(localDate.dayOfMonth, localDate.monthValue, localDate.year)
        _uiState.update { it.copy(getMealPlanResponse = Response.Loading) }
        repo.getMealPlanFromFirestore(dayId).collect { res ->
            if (res is Response.Success && res.data != null) {
                _uiState.update {
                    it.copy(
                        dailyMealPlan = res.data, getMealPlanResponse = Response.Success(true)
                    )
                }
                updateNutrientsInformation(res.data)
            } else {
                getDailyMealPlanFromSpoonacular()
            }
        }
    }

    private fun getDailyMealPlanFromSpoonacular() = viewModelScope.launch {
        _uiState.update { it.copy(getMealPlanResponse = Response.Loading) }
        repo.getMealPlanWithCalories(WecareCaloriesObject.getInstance().caloriesInEachDay)
            .collect { res ->
                if (res is Response.Success) {
                    _uiState.update { it.copy(getMealPlanResponse = Response.Success(true)) }
                    getRecipeDetailInformationAndInsertDetailToFirestore(res.data.meals)
                } else {
                    _uiState.update { it.copy(getMealPlanResponse = Response.Error(Exception("Fail to get meal plan!"))) }
                }
            }
    }

    private fun getRecipeDetailInformationAndInsertDetailToFirestore(mealsPlan: List<MealPlan>) =
        viewModelScope.launch {
            val recipes = arrayListOf<RecipeInformation>()
            for (item in mealsPlan) {
                repo.getRecipeInformationWithId(item.id).collect { res ->
                    if (res is Response.Success) {
                        repo.insertMealPlanToFirestore(
                            dayId = getCurrentDayId(), recipe = res.data
                        ).collect {}
                        recipes.add(res.data)
                    }
                }
            }
            updateNutrientsInformation(recipes)
            _uiState.update { it.copy(dailyMealPlan = recipes) }
        }

    private fun updateNutrientsInformation(recipes: List<RecipeInformation>) {
        var totalCalories = 0
        var totalProtein = 0
        var totalFat = 0
        var totalCarbs = 0
        for (i in recipes) {
            totalCalories += i.nutrition.nutrients[0].amount.toInt()
            totalProtein += i.nutrition.nutrients[8].amount.toInt()
            totalFat += i.nutrition.nutrients[1].amount.toInt()
            totalCarbs += i.nutrition.nutrients[3].amount.toInt()
        }
        _uiState.update {
            it.copy(
                totalCalories = totalCalories,
                totalProtein = totalProtein,
                totalFat = totalFat,
                totalCarbs = totalCarbs
            )
        }
    }

    fun resetGetMealResponse() {
        _uiState.update { it.copy(getMealPlanResponse = null, addMealResponse = null) }
    }
}
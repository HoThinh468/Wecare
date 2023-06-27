package com.vn.wecare.feature.food.dashboard.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentDailyNutritionBinding
import com.vn.wecare.feature.food.dashboard.viewmodel.NutritionDashboardViewmodel
import com.vn.wecare.utils.safeNavigate

class FoodDashboardFragment :
    BaseBindingFragment<FragmentDailyNutritionBinding>(FragmentDailyNutritionBinding::inflate) {

    private val viewModel: NutritionDashboardViewmodel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        viewModel.initUiState()
        super.setupComposeView(
            binding.dailyNutritionComposeView
        ) {
            NutritionDashboardScreen(
                moveToBreakfastScreen = {
                    findNavController().safeNavigate(
                        R.id.dailyNutritionFragment,
                        R.id.action_dailyNutritionFragment_to_breakfastFragment
                    )
                },
                moveToLunchScreen = {
                    findNavController().safeNavigate(
                        R.id.dailyNutritionFragment,
                        R.id.action_dailyNutritionFragment_to_lunchFragment
                    )
                },
                moveToSnackScreen = {
                    findNavController().safeNavigate(
                        R.id.dailyNutritionFragment,
                        R.id.action_dailyNutritionFragment_to_snackFragment
                    )
                },
                moveToDinnerScreen = {
                    findNavController().safeNavigate(
                        R.id.dailyNutritionFragment,
                        R.id.action_dailyNutritionFragment_to_dinnerFragment
                    )
                },
                moveToAddMealScreen = {
                    val bundle = bundleOf(KEY_FOR_INDEX_OF_MEAL to it)
                    findNavController().navigate(
                        R.id.action_global_addMealFragment, bundle
                    )
                },
                moveToSearchFoodScreen = {
                    findNavController().safeNavigate(
                        R.id.dailyNutritionFragment,
                        R.id.action_dailyNutritionFragment_to_searchFoodFragment
                    )
                },
                moveToAddYourOwnMealsListScreen = {
                    findNavController().safeNavigate(
                        R.id.dailyNutritionFragment,
                        R.id.action_dailyNutritionFragment_to_yourOwnMealListFragment
                    )
                },
                moveToReportScreen = {
                    findNavController().safeNavigate(
                        R.id.dailyNutritionFragment,
                        R.id.action_dailyNutritionFragment_to_foodReportFragment
                    )
                },
                nutritionDashboardViewmodel = viewModel,
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetNutrientIndex()
    }

    companion object {
        const val KEY_FOR_INDEX_OF_MEAL = "index"
    }
}
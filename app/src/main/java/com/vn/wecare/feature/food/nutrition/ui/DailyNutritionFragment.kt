package com.vn.wecare.feature.food.nutrition.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentDailyNutritionBinding
import com.vn.wecare.feature.food.nutrition.viewmodel.DailyNutritionViewmodel
import com.vn.wecare.utils.safeNavigate
import com.vn.wecare.utils.safeNavigateWithBundle

class DailyNutritionFragment :
    BaseBindingFragment<FragmentDailyNutritionBinding>(FragmentDailyNutritionBinding::inflate) {

    private val viewModel: DailyNutritionViewmodel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(
            binding.dailyNutritionComposeView
        ) {
            DailyNutritionScreen(
                moveToBreakfastScreen = {
                    findNavController().safeNavigate(
                        R.id.dailyNutritionFragment,
                        R.id.action_dailyNutritionFragment_to_breakfastFragment
                    )
                },
                moveToLunchScreen = {},
                moveToSnackScreen = {},
                moveToDinnerScreen = {},
                moveToAddMealScreen = {
                    val bundle = bundleOf(KEY_FOR_INDEX_OF_MEAL to it)
                    findNavController().safeNavigateWithBundle(
                        R.id.dailyNutritionFragment,
                        R.id.action_dailyNutritionFragment_to_addMealFragment,
                        bundle
                    )
                },
                dailyNutritionViewmodel = viewModel
            )
        }
    }

    companion object {
        const val KEY_FOR_INDEX_OF_MEAL = "index"
    }
}
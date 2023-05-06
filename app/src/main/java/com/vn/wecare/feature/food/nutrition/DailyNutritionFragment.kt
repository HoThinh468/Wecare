package com.vn.wecare.feature.food.nutrition

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentDailyNutritionBinding
import com.vn.wecare.utils.safeNavigate

class DailyNutritionFragment :
    BaseBindingFragment<FragmentDailyNutritionBinding>(FragmentDailyNutritionBinding::inflate) {

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(
            binding.dailyNutritionComposeView
        ) {
            DailyNutritionScreen(moveToBreakfastScreen = {
                findNavController().safeNavigate(
                    R.id.dailyNutritionFragment,
                    R.id.action_dailyNutritionFragment_to_breakfastFragment
                )
            },
                moveToLunchScreen = {},
                moveToSnackScreen = {},
                moveToDinnerScreen = {},
                moveToAddMealScreen = {
                    findNavController().safeNavigate(
                        R.id.dailyNutritionFragment,
                        R.id.action_dailyNutritionFragment_to_addMealFragment
                    )
                })
        }
    }
}
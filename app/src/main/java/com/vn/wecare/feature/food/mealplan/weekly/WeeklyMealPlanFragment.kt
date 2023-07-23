package com.vn.wecare.feature.food.mealplan.weekly

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentMealPlanBinding
import com.vn.wecare.utils.safeNavigate

class WeeklyMealPlanFragment : BaseBindingFragment<FragmentMealPlanBinding>(
    FragmentMealPlanBinding::inflate
) {

    private val viewModel: WeeklyMealPlanViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(binding.mealPlanComposeView) {
            WeeklyMealPlanScreen(navigateUp = { findNavController().popBackStack() },
                moveToDailyMealPlanScreen = {
                    findNavController().safeNavigate(
                        R.id.mealPlanFragment, R.id.action_mealPlanFragment_to_dailyMealPlanFragment
                    )
                },
                viewModel = viewModel
            )
        }
    }
}
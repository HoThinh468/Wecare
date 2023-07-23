package com.vn.wecare.feature.food.mealplan.daily

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentDailyMealPlanBinding

class DailyMealPlanFragment : BaseBindingFragment<FragmentDailyMealPlanBinding>(
    FragmentDailyMealPlanBinding::inflate
) {

    private val viewmodel: DailyMealPlanViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(binding.dailyMealPlanComposeView) {
            DailyMealPlanScreen(navigateUp = {
                findNavController().popBackStack()
            }, viewModel = viewmodel)
        }
    }

    companion object {
        const val dailyMealPlanTag = "Daily meal plan flow"
    }
}
package com.vn.wecare.feature.food.mealplan.daily

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentDailyMealPlanBinding
import com.vn.wecare.feature.food.mealplan.weekly.MEAL_PLAN_DAY_OF_MONTH
import com.vn.wecare.feature.food.mealplan.weekly.MEAL_PLAN_MONTH
import com.vn.wecare.feature.food.mealplan.weekly.MEAL_PLAN_YEAR
import java.time.LocalDate

class DailyMealPlanFragment : BaseBindingFragment<FragmentDailyMealPlanBinding>(
    FragmentDailyMealPlanBinding::inflate
) {

    private val viewmodel: DailyMealPlanViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        val dayOfMonth = arguments?.getInt(MEAL_PLAN_DAY_OF_MONTH) ?: 1
        val month = arguments?.getInt(MEAL_PLAN_MONTH) ?: 1
        val year = arguments?.getInt(MEAL_PLAN_YEAR) ?: 2000

        val localDate = LocalDate.of(year, month, dayOfMonth)
        viewmodel.initUiState(localDate)
        super.setupComposeView(binding.dailyMealPlanComposeView) {
            DailyMealPlanScreen(navigateUp = {
                findNavController().popBackStack()
            }, viewModel = viewmodel, localDate = localDate)
        }
    }

    companion object {
        const val dailyMealPlanTag = "Daily meal plan flow"
    }
}
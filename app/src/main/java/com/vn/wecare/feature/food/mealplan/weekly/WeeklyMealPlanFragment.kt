package com.vn.wecare.feature.food.mealplan.weekly

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentMealPlanBinding

const val MEAL_PLAN_DAY_OF_MONTH = "dayOfMonth"
const val MEAL_PLAN_MONTH = "month"
const val MEAL_PLAN_YEAR = "year"

class WeeklyMealPlanFragment : BaseBindingFragment<FragmentMealPlanBinding>(
    FragmentMealPlanBinding::inflate
) {

    private val viewModel: WeeklyMealPlanViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(binding.mealPlanComposeView) {
            WeeklyMealPlanScreen(
                navigateUp = { findNavController().popBackStack() },
                moveToDailyMealPlanScreen = { dayOfMonth, month, year ->
                    val bundle = Bundle()
                    bundle.apply {
                        putInt(MEAL_PLAN_DAY_OF_MONTH, dayOfMonth)
                        putInt(MEAL_PLAN_MONTH, month)
                        putInt(MEAL_PLAN_YEAR, year)
                    }
                    findNavController().navigate(
                        R.id.action_mealPlanFragment_to_dailyMealPlanFragment, bundle
                    )
                },
                viewModel = viewModel
            )
        }
    }
}
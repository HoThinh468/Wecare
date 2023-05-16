package com.vn.wecare.feature.food.breakfast.ui

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentBreakfastBinding
import com.vn.wecare.feature.food.breakfast.viewmodel.BreakfastViewModel
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.utils.safeNavigate

const val MEAL_KEY = "MEAL_KEY"
const val MEAL_RECORD = "MEAL_RECORD"

class BreakfastFragment : BaseBindingFragment<FragmentBreakfastBinding>(
    FragmentBreakfastBinding::inflate
) {

    private val breakfastViewModel: BreakfastViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        breakfastViewModel.reUpdateUiState()
        super.setupComposeView(
            binding.breakfastComposeView
        ) {
            BreakfastScreen(navigateUp = {
                findNavController().popBackStack()
            },
                moveToAddMealScreen = {
                    findNavController().safeNavigate(
                        R.id.breakfastFragment, R.id.action_breakfastFragment_to_addMealFragment
                    )
                },
                breakfastViewModel = breakfastViewModel,
                navigateToDetailScreen = { meal ->
                    val bundle = Bundle()
                    bundle.putParcelable(MEAL_RECORD, meal)
                    bundle.putSerializable(MEAL_KEY, MealTypeKey.BREAKFAST)
                    findNavController().navigate(R.id.action_global_mealDetailFragment, bundle)
                })
        }
    }
}
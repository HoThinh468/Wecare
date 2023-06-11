package com.vn.wecare.feature.food.snack

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentSnackBinding
import com.vn.wecare.feature.food.breakfast.ui.MEAL_KEY
import com.vn.wecare.feature.food.breakfast.ui.MEAL_RECORD
import com.vn.wecare.feature.food.dashboard.ui.FoodDashboardFragment
import com.vn.wecare.feature.food.data.model.MealTypeKey

class SnackFragment : BaseBindingFragment<FragmentSnackBinding>(
    FragmentSnackBinding::inflate
) {

    private val viewModel: SnackViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(
            binding.snackComposeView
        ) {
            viewModel.reUpdateUiState()
            SnackScreen(
                navigateUp = { findNavController().popBackStack() },
                moveToAddMealScreen = {
                    val bundle = bundleOf(FoodDashboardFragment.KEY_FOR_INDEX_OF_MEAL to it)
                    findNavController().navigate(
                        R.id.action_global_addMealFragment, bundle
                    )
                },
                navigateToDetailScreen = { meal ->
                    val bundle = Bundle()
                    bundle.putParcelable(MEAL_RECORD, meal)
                    bundle.putSerializable(MEAL_KEY, MealTypeKey.SNACK)
                    findNavController().navigate(R.id.action_global_mealDetailFragment, bundle)
                },
                viewModel = viewModel,
            )
        }
    }
}
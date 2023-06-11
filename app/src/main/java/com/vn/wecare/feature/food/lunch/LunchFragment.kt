package com.vn.wecare.feature.food.lunch

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentLunchBinding
import com.vn.wecare.feature.food.breakfast.ui.MEAL_KEY
import com.vn.wecare.feature.food.breakfast.ui.MEAL_RECORD
import com.vn.wecare.feature.food.dashboard.ui.FoodDashboardFragment
import com.vn.wecare.feature.food.data.model.MealTypeKey

class LunchFragment : BaseBindingFragment<FragmentLunchBinding>(
    FragmentLunchBinding::inflate
) {

    private val lunchViewModel: LunchViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        lunchViewModel.reUpdateUiState()
        super.setupComposeView(
            binding.lunchComposeView
        ) {
            LunchScreen(
                navigateUp = {
                    findNavController().popBackStack()
                },
                moveToAddMealScreen = {
                    val bundle = bundleOf(FoodDashboardFragment.KEY_FOR_INDEX_OF_MEAL to it)
                    findNavController().navigate(
                        R.id.action_global_addMealFragment, bundle
                    )
                },
                viewModel = lunchViewModel,
                navigateToDetailScreen = {
                    val bundle = Bundle()
                    bundle.putParcelable(MEAL_RECORD, it)
                    bundle.putSerializable(MEAL_KEY, MealTypeKey.LUNCH)
                    findNavController().navigate(R.id.action_global_mealDetailFragment, bundle)
                }
            )
        }
    }
}
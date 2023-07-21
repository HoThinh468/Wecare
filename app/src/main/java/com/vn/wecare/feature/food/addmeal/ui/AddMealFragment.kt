package com.vn.wecare.feature.food.addmeal.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentAddMealBinding
import com.vn.wecare.feature.food.addmeal.viewmodel.AddMealViewModel
import com.vn.wecare.feature.food.dashboard.ui.FoodDashboardFragment
import com.vn.wecare.utils.safeNavigate

class AddMealFragment : BaseBindingFragment<FragmentAddMealBinding>(
    FragmentAddMealBinding::inflate
) {

    private val addMealViewModel: AddMealViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        addMealViewModel.getMealsOfAllTypeList()
        val index = arguments?.getInt(FoodDashboardFragment.KEY_FOR_INDEX_OF_MEAL)
        super.setupComposeView(
            binding.addMealComposeView
        ) {
            AddMealScreen(
                navigateUp = {
                    findNavController().popBackStack()
                },
                viewModel = addMealViewModel,
                index = index ?: 0,
                moveToSearchMealScreen = {
                    findNavController().safeNavigate(
                        R.id.addMealFragment,
                        R.id.action_addMealFragment_to_searchFoodFragment
                    )
                },
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        addMealViewModel.resetInsertMealRecordResponse()
        addMealViewModel.resetMealListOfAllType()
    }

    companion object {
        const val addMealTag = "Add meal flow"
    }
}
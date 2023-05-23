package com.vn.wecare.feature.food.addmeal.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentAddMealBinding
import com.vn.wecare.feature.food.addmeal.viewmodel.AddMealViewModel
import com.vn.wecare.feature.food.dashboard.ui.FoodDashboardFragment

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
            val meals = listOf(
                addMealViewModel.breakFastMealList.collectAsLazyPagingItems(),
                addMealViewModel.getLunchMealsByNutrients().collectAsLazyPagingItems(),
//                addMealViewModel.getSnackMealsByNutrients().collectAsLazyPagingItems(),
//                addMealViewModel.getDinnerMealsByNutrients().collectAsLazyPagingItems()
            )
            AddMealScreen(navigateUp = {
                findNavController().popBackStack()
            }, addMealViewModel = addMealViewModel, index = index ?: 0, meals = meals)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        addMealViewModel.resetInsertMealRecordResponse()
    }

    companion object {
        const val addMealTag = "Add meal flow"
    }
}
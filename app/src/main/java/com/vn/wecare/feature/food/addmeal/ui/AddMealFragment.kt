package com.vn.wecare.feature.food.addmeal.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentAddMealBinding
import com.vn.wecare.feature.food.addmeal.viewmodel.AddMealViewModel
import com.vn.wecare.feature.food.nutrition.ui.DailyNutritionFragment

class AddMealFragment : BaseBindingFragment<FragmentAddMealBinding>(
    FragmentAddMealBinding::inflate
) {

    private val addMealViewModel: AddMealViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        val index = arguments?.getInt(DailyNutritionFragment.KEY_FOR_INDEX_OF_MEAL)
        super.setupComposeView(
            binding.addMealComposeView
        ) {
            AddMealScreen(navigateUp = {
                findNavController().popBackStack()
            }, addMealViewModel = addMealViewModel, index = index ?: 0)
        }
    }

    companion object {
        const val addMealTag = "Add meal flow"
    }
}
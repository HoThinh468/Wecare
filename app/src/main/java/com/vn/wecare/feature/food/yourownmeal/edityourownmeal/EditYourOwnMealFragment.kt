package com.vn.wecare.feature.food.yourownmeal.edityourownmeal

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentEditYourOwnMealBinding
import com.vn.wecare.feature.food.breakfast.ui.MEAL_RECORD
import com.vn.wecare.feature.food.data.model.Meal
import com.vn.wecare.feature.food.data.model.MealRecordModel

const val MEAL = "MEAL"

class EditYourOwnMealFragment : BaseBindingFragment<FragmentEditYourOwnMealBinding>(
    FragmentEditYourOwnMealBinding::inflate
) {

    private val viewModel: EditYourOwnMealViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        val meal: Meal = arguments?.getParcelable(MEAL) ?: Meal()
        viewModel.initEditMealUiState(meal)
        super.setupComposeView(binding.editYourOwnMealComposeView) {
            EditYourOwnMealScreen(
                navigateBack = { findNavController().popBackStack() }, viewModel = viewModel
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetUiState()
    }
}
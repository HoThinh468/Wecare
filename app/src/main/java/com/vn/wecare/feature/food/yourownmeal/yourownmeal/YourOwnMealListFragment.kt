package com.vn.wecare.feature.food.yourownmeal.yourownmeal

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentYourOwnMealListBinding
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.utils.safeNavigate

class YourOwnMealListFragment : BaseBindingFragment<FragmentYourOwnMealListBinding>(
    FragmentYourOwnMealListBinding::inflate
) {

    private val viewModel: YourOwnMealViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        viewModel.initMealsOfAllTypeList()
        super.setupComposeView(
            binding.yourOwnMealListComposeView
        ) {
            viewModel.onMealCategoryChosen(MealTypeKey.BREAKFAST)
            YourOwnMealListScreen(
                navigateBack = { findNavController().popBackStack() },
                viewModel = viewModel,
                moveToAddYourOwnMealScreen = {
                    findNavController().safeNavigate(
                        R.id.yourOwnMealListFragment,
                        R.id.action_yourOwnMealListFragment_to_addYourOwnMealFragment
                    )
                },
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetUIState()
        viewModel.resetMealsOfAllTypeList()
    }

    companion object {
        const val yourOwnMealTag = "Your own meal flow"
    }
}
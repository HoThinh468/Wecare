package com.vn.wecare.feature.food.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentSearchFoodBinding
import com.vn.wecare.feature.food.search.SearchFoodViewModel
import com.vn.wecare.utils.safeNavigate

class SearchFoodFragment : BaseBindingFragment<FragmentSearchFoodBinding>(
    FragmentSearchFoodBinding::inflate
) {

    private val viewModel: SearchFoodViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        viewModel.initMealsOfAllTypeList()
        super.setupComposeView(
            binding.searchFoodComposeView
        ) {
            SearchFoodScreen(
                navigateBack = {
                    findNavController().popBackStack()
                },
                viewModel = viewModel,
                moveToAddYourOwnMealScreen = {
                    findNavController().safeNavigate(
                        R.id.searchFoodFragment,
                        R.id.action_searchFoodFragment_to_addYourOwnMealFragment
                    )
                },
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.apply {
            resetMealsOfAllTypeList()
            resetAddMealRecordResult()
        }
    }

    companion object {
        const val searchMealTag = "Search meal flow"
    }
}
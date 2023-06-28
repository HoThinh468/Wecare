package com.vn.wecare.feature.food.report

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentFoodReportBinding

class FoodReportFragment : BaseBindingFragment<FragmentFoodReportBinding>(
    FragmentFoodReportBinding::inflate
) {

    private val viewModel: FoodReportViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        viewModel.initReportView()
        super.setupComposeView(
            binding.foodReportComposeView
        ) {
            FoodReportScreen(
                navigateBack = { findNavController().popBackStack() }, viewModel = viewModel
            )
        }
    }

    companion object {
        const val foodReportTag = "Food report flow"
    }
}
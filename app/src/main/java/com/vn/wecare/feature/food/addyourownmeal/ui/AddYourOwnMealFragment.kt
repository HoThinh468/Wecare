package com.vn.wecare.feature.food.addyourownmeal.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentAddYourOwnMealBinding
import com.vn.wecare.feature.food.addyourownmeal.AddYourOwnMealViewModel

class AddYourOwnMealFragment : BaseBindingFragment<FragmentAddYourOwnMealBinding>(
    FragmentAddYourOwnMealBinding::inflate
) {

    private val viewModel: AddYourOwnMealViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(
            binding.addYourOwnMealComposeView
        ) {
            AddYourOwnMealScreen(
                navigateBack = { findNavController().popBackStack() }, viewModel = viewModel
            )
        }
    }
}
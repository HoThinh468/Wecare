package com.vn.wecare.feature.home.bmi.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentBMIBinding
import com.vn.wecare.feature.home.bmi.viewmodel.BMIViewModel

class BMIFragment : BaseBindingFragment<FragmentBMIBinding>(FragmentBMIBinding::inflate) {

    private val viewModel: BMIViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(binding.bmiComposeView) {
            BMIScreen(
                navigateUp = {
                    findNavController().popBackStack()
                }, viewModel = viewModel,
                onNavigateToHistory = {
                    findNavController().navigate(R.id.action_BMIFragment_to_BMIHistoryFragment)
                }
            )
        }
    }

    companion object {
        const val bmiFlowTag = "BMI flow"
    }
}
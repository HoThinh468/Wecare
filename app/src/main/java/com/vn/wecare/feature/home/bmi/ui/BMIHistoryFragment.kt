package com.vn.wecare.feature.home.bmi.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentBMIHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BMIHistoryFragment :
    BaseBindingFragment<FragmentBMIHistoryBinding>(FragmentBMIHistoryBinding::inflate) {

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(binding.bmiHistoryComposeView) {
            BMIHistoryScreen(
                onNavigationBack = {
                    findNavController().popBackStack()
                }
            )
        }
    }
}
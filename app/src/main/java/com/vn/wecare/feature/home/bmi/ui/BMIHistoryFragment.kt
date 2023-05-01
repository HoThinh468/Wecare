package com.vn.wecare.feature.home.bmi.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentBMIHistoryBinding

class BMIHistoryFragment :
    BaseBindingFragment<FragmentBMIHistoryBinding>(FragmentBMIHistoryBinding::inflate) {

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(binding.bmiHistoryComposeView) {

        }
    }
}
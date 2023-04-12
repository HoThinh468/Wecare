package com.vn.wecare.feature.home.water.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentWaterBinding
import com.vn.wecare.feature.home.water.WaterScreen

class WaterFragment : BaseBindingFragment<FragmentWaterBinding>(FragmentWaterBinding::inflate) {
    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(binding.waterComposeView) {
            WaterScreen(onNavigateUp = { findNavController().popBackStack() })
        }
    }
}
package com.vn.wecare.feature.home.water

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentWaterBinding

class WaterFragment : BaseBindingFragment<FragmentWaterBinding>(FragmentWaterBinding::inflate) {
    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(binding.waterComposeView) {
            WaterScreen(onNavigateUp = { findNavController().popBackStack() })
        }
    }
}
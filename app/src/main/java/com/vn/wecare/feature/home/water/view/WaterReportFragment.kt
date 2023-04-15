package com.vn.wecare.feature.home.water.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentWaterReportBinding
import com.vn.wecare.feature.home.water.composable.WaterReportScreen

class WaterReportFragment :
    BaseBindingFragment<FragmentWaterReportBinding>(FragmentWaterReportBinding::inflate) {

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(binding.waterReportComposeView) {
            WaterReportScreen() {
                findNavController().popBackStack()
            }
        }
    }
}
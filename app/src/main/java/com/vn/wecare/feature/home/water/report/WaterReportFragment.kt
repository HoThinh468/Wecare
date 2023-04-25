package com.vn.wecare.feature.home.water.report

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentWaterReportBinding

class WaterReportFragment :
    BaseBindingFragment<FragmentWaterReportBinding>(FragmentWaterReportBinding::inflate) {

    private val viewModel: WaterReportViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        viewModel.initReportView()
        super.setupComposeView(binding.waterReportComposeView) {
            WaterReportScreen(viewModel = viewModel, onNavigateUp = {
                findNavController().popBackStack()
            })
        }
    }

    companion object {
        const val waterReportTag = "Water report flow"
    }
}
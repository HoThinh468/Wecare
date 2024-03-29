package com.vn.wecare.feature.home.water.tracker.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentWaterBinding
import com.vn.wecare.feature.home.water.tracker.WaterViewModel

class WaterFragment : BaseBindingFragment<FragmentWaterBinding>(FragmentWaterBinding::inflate) {

    private val viewModel: WaterViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        viewModel.initWaterView()
        super.setupComposeView(binding.waterComposeView) {
            WaterScreen(
                onNavigateUp = { findNavController().popBackStack() },
                moveToReportScreen = { findNavController().navigate(R.id.action_waterFragment_to_waterReportFragment) },
                viewModel = viewModel
            )
        }
    }

    companion object {
        const val waterTrackerTag = "Water tracker flow"
    }
}
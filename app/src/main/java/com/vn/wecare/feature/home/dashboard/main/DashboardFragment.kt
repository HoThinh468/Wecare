package com.vn.wecare.feature.home.dashboard.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentDashboardBinding

class DashboardFragment : BaseBindingFragment<FragmentDashboardBinding>(
    FragmentDashboardBinding::inflate
) {

    private val viewModel: DashboardViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        viewModel.initDashboardUiState()
        super.setupComposeView(
            binding.dashboardComposeView
        ) {
            DashboardScreen(
                navigateBack = {
                    findNavController().popBackStack()
                }, viewModel = viewModel
            )
        }
    }

    companion object {
        const val dashboardTag = "Home dashboard tag"
    }
}
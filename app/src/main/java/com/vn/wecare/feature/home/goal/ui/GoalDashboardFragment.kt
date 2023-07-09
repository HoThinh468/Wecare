package com.vn.wecare.feature.home.goal.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentDashboardBinding
import com.vn.wecare.feature.home.goal.GoalDashboardViewModel

class GoalDashboardFragment : BaseBindingFragment<FragmentDashboardBinding>(
    FragmentDashboardBinding::inflate
) {

    private val viewModel: GoalDashboardViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        viewModel.initDashboardUiState()
        super.setupComposeView(
            binding.dashboardComposeView
        ) {
            GoalDashboardScreen(
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
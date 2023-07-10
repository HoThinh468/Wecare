package com.vn.wecare.feature.home.goal.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.core.checkInternetConnection
import com.vn.wecare.databinding.FragmentDashboardBinding

class GoalDashboardFragment : BaseBindingFragment<FragmentDashboardBinding>(
    FragmentDashboardBinding::inflate
) {

    private val viewModel: GoalDashboardViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        viewModel.checkIfInternetIsAvailable(
            checkInternetConnection(requireContext())
        )
        viewModel.initUI()
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
        const val goalDashboardTag = "Goal dashboard tag"
    }
}
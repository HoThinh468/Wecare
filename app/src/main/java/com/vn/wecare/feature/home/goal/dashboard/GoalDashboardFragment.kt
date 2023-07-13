package com.vn.wecare.feature.home.goal.dashboard

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.core.checkInternetConnection
import com.vn.wecare.databinding.FragmentDashboardBinding

const val WEEKLY_RECORD = "WEEKLY_RECORD"

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
                },
                viewModel = viewModel,
                navigateWeeklyRecordScreen = { record ->
                    val bundle = Bundle()
                    bundle.putParcelable(WEEKLY_RECORD, record)
                    findNavController().navigate(
                        R.id.action_dashboardFragment_to_weeklyRecordDetailFragment2, bundle
                    )
                },
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetGetResponseData()
    }

    companion object {
        const val goalDashboardTag = "Goal dashboard tag"
    }
}
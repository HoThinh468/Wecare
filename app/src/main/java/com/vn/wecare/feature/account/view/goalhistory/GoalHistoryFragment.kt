package com.vn.wecare.feature.account.view.goalhistory

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentGoalHistoryBinding
import com.vn.wecare.feature.account.viewmodel.GoalHistoryViewModel

const val GOAL_PARCElABLE_KEY = "GOAL_PARCELABLE"

class GoalHistoryFragment :
    BaseBindingFragment<FragmentGoalHistoryBinding>(FragmentGoalHistoryBinding::inflate) {

    private val viewModel: GoalHistoryViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        viewModel.initGoalHistoryUi()
        super.setupComposeView(
            binding.goalHistoryComposeView
        ) {
            GoalHistoryScreen(
                navigateBack = { findNavController().popBackStack() },
                viewModel = viewModel,
                moveToEditInfoScreen = {
                    val bundle = Bundle()
                    bundle.putParcelable(GOAL_PARCElABLE_KEY, it)
                    findNavController().navigate(
                        R.id.action_goalHistoryFragment_to_editInformationFragment, bundle
                    )
                },
            )
        }
    }

    companion object {
        const val GoalHistoryTag = "Goal history flow"
    }
}
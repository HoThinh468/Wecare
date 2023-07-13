package com.vn.wecare.feature.home.goal.weeklyrecords

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentWeeklyRecordDetailBinding
import com.vn.wecare.feature.home.goal.dashboard.WEEKLY_RECORD
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord

class WeeklyRecordDetailFragment : BaseBindingFragment<FragmentWeeklyRecordDetailBinding>(
    FragmentWeeklyRecordDetailBinding::inflate
) {

    private val viewModel: WeeklyRecordViewModel by activityViewModels()

    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        val record: GoalWeeklyRecord = arguments?.getParcelable(WEEKLY_RECORD) ?: GoalWeeklyRecord()
        viewModel.initUiState(record)
        super.setupComposeView(
            binding.weeklyRecordDetailComposeView
        ) {
            GoalWeeklyRecordDetailScreen(navigateBack = {
                findNavController().popBackStack()
            }, viewModel = viewModel)
        }
    }
}
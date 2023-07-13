package com.vn.wecare.feature.account.view.editinfo

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentEditInformationBinding
import com.vn.wecare.feature.account.view.goalhistory.GOAL_PARCElABLE_KEY
import com.vn.wecare.feature.account.viewmodel.EditInfoViewModel
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.Goal

class EditInformationFragment : BaseBindingFragment<FragmentEditInformationBinding>(
    FragmentEditInformationBinding::inflate
) {

    private val viewModel: EditInfoViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        val goal: Goal =
            arguments?.getParcelable(GOAL_PARCElABLE_KEY) ?: LatestGoalSingletonObject.getInStance()
        Log.d(editTag, LatestGoalSingletonObject.getInStance().toString())
        viewModel.initEditInfoScreenUiState(goal)
        super.setupComposeView(
            binding.editInfoComposeView
        ) {
            EditInfoScreen(
                navigateBack = { findNavController().popBackStack() }, viewModel = viewModel
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetUiState()
    }

    companion object {
        const val editTag = "Edit info flow"
    }
}
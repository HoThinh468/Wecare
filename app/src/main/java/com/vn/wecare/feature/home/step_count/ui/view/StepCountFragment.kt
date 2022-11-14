package com.vn.wecare.feature.home.step_count.ui.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentStepCountBinding
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.feature.home.step_count.ui.compose.StepCountScreen

class StepCountFragment :
    BaseBindingFragment<FragmentStepCountBinding>(FragmentStepCountBinding::inflate) {

    private val stepCountViewModel: StepCountViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(
            binding.stepCountComposeView
        ) {
            val stepsCountUiState = stepCountViewModel.stepsCountUiState.collectAsState()
            StepCountScreen(
                navigateUp = { findNavController().popBackStack() },
                moveToSetGoalScreen = {
                    findNavController()
                        .navigate(R.id.action_stepCountFragment_to_setYourGoalsFragment)
                }
            )
        }
    }
}
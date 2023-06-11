package com.vn.wecare.feature.home.step_count.ui.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentStepCountBinding
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.feature.home.step_count.ui.compose.StepCountScreen
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class StepCountFragment :
    BaseBindingFragment<FragmentStepCountBinding>(FragmentStepCountBinding::inflate) {
    private val stepCountViewModel: StepCountViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        stepCountViewModel.initUIState()
        super.setupComposeView(
            binding.stepCountComposeView
        ) {
            stepCountViewModel.initUIState()
            StepCountScreen(
                navigateUp = { findNavController().popBackStack() },
                stepCountViewModel = stepCountViewModel
            )
        }
    }

    companion object {
        const val stepCountTag = "Step count flow"
    }
}
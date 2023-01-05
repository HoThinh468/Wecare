package com.vn.wecare.feature.home.step_count.ui.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentSetYourGoalsBinding
import com.vn.wecare.feature.home.step_count.ui.compose.SetYourGoalScreen

class SetYourGoalsFragment: BaseBindingFragment<FragmentSetYourGoalsBinding>(FragmentSetYourGoalsBinding::inflate) {
    @OptIn(ExperimentalMaterialApi::class)
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(
            binding.setYourGoalsComposeView
        ) {
            SetYourGoalScreen(
                navigateUp = { findNavController().popBackStack() },
            )
        }
    }
}
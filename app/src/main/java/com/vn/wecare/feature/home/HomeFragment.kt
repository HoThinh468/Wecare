package com.vn.wecare.feature.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.databinding.FragmentHomeBinding
import com.vn.wecare.feature.home.view.HomeScreen

class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(
            binding.homeComposeView
        ) {
            HomeScreen(
                onFootStepCountCardClick = {
                    findNavController()
                        .navigate(R.id.action_homeFragment_to_stepCountFragment)
                },
                onTrainingClick = {
                    findNavController()
                        .navigate(R.id.action_homeFragment_to_trainingFragment)
                },
                onWaterCardClick = {},
                onBMICardClick = {},
                onWalkingIcClick = {},
                onRunningIcClick = {},
                onBicycleIcClick = {},
                onMeditationIcClick = {}
            )
        }
    }
}
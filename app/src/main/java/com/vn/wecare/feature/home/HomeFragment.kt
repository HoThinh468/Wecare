package com.vn.wecare.feature.home

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.core.alarm.ExactAlarms
import com.vn.wecare.databinding.FragmentHomeBinding
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    @Inject
    lateinit var stepCountExactAlarms: ExactAlarms

    private val stepCountViewModel: StepCountViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(
            binding.homeComposeView
        ) {
            HomeScreen(
                onFootStepCountCardClick = {
                    findNavController().navigate(R.id.action_homeFragment_to_stepCountFragment)
                },
                onTrainingClick = {
                    findNavController().navigate(R.id.action_homeFragment_to_trainingFragment)
                },
                onWaterCardClick = {},
                onBMICardClick = {},
                onWalkingIcClick = {},
                onRunningIcClick = {},
                onBicycleIcClick = {},
                onMeditationIcClick = {},
                stepCountViewModel = stepCountViewModel
            )
        }
    }

    override fun setupWhatNeeded() {
        super.setupWhatNeeded()
        // Open dialog to request for schedule exact alarm
        if (stepCountExactAlarms.canScheduleExactAlarm()) {
            // stepCountExactAlarms.scheduleExactAlarm(System.currentTimeMillis(), 30000)
        } else {
            openSetting()
        }
    }

    // Call this function in the main screen to get needed permissions
    private fun openSetting() {
        // If android is higher than android 12 than open the settings to allow permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
        }
    }
}
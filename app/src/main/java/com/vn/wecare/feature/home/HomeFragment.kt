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
import com.vn.wecare.core.alarm.InExactAlarms
import com.vn.wecare.databinding.FragmentHomeBinding
import com.vn.wecare.feature.home.bmi.viewmodel.BMIViewModel
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.feature.home.water.tracker.WaterViewModel
import com.vn.wecare.utils.getEndOfTheDayMilliseconds
import com.vn.wecare.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    @Inject
    lateinit var stepCountExactAlarms: ExactAlarms

    @Inject
    lateinit var stepCountInExactAlarms: InExactAlarms

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val stepCountViewModel: StepCountViewModel by activityViewModels()
    private val waterViewModel: WaterViewModel by activityViewModels()
    private val bmiViewModel: BMIViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        super.setupComposeView(
            binding.homeComposeView
        ) {
            HomeScreen(
                onFootStepCountCardClick = {
                    findNavController().safeNavigate(
                        R.id.homeFragment, R.id.action_homeFragment_to_stepCountFragment
                    )
                },
                onTrainingClick = {
                    findNavController().safeNavigate(
                        R.id.homeFragment, R.id.action_homeFragment_to_trainingFragment
                    )
                },
                onWaterCardClick = {
                    findNavController().safeNavigate(
                        R.id.homeFragment, R.id.action_homeFragment_to_water_graph
                    )
                },
                onBMICardClick = {
                    findNavController().safeNavigate(
                        R.id.homeFragment, R.id.action_homeFragment_to_bmi_graph
                    )
                },
                onWalkingIcClick = {},
                onRunningIcClick = {},
                onBicycleIcClick = {},
                onMeditationIcClick = {},
                cancelInExactAlarm = { homeViewModel.cancelInExactAlarm() },
                homeViewModel = homeViewModel,
                stepCountViewModel = stepCountViewModel,
                waterViewModel = waterViewModel,
                bmiViewModel = bmiViewModel
            )
        }
    }

    override fun setupWhatNeeded() {
        super.setupWhatNeeded()
        setupStepCountAlarm()
    }

    private fun setupStepCountAlarm() {
        if (stepCountExactAlarms.canScheduleExactAlarm()) {
            if (!stepCountExactAlarms.isExactAlarmSet()) {
                stepCountExactAlarms.scheduleExactAlarm(getEndOfTheDayMilliseconds())
            }
        } else openSetting()
    }

    private fun openSetting() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
        }
    }
}
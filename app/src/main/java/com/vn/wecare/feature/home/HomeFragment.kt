package com.vn.wecare.feature.home

import android.content.Context
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
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.feature.home.step_count.alarm.IS_STEP_COUNT_INEXACT_ALARM_SET
import com.vn.wecare.feature.home.step_count.alarm.STEP_COUNT_ALARM
import com.vn.wecare.feature.home.water.tracker.WaterViewModel
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
                onWaterCardClick = {
                    findNavController().navigate(R.id.action_homeFragment_to_water_graph)
                },
                onBMICardClick = {},
                onWalkingIcClick = {},
                onRunningIcClick = {},
                onBicycleIcClick = {},
                onMeditationIcClick = {},
                cancelInExactAlarm = { homeViewModel.cancelInExactAlarm() },
                homeViewModel = homeViewModel,
                stepCountViewModel = stepCountViewModel,
                waterViewModel = waterViewModel
            )
        }
    }

    override fun setupWhatNeeded() {
        super.setupWhatNeeded()
        val sharedPref =
            requireActivity().getSharedPreferences(STEP_COUNT_ALARM, Context.MODE_PRIVATE)
        if (stepCountExactAlarms.canScheduleExactAlarm()) {
//            stepCountExactAlarms.scheduleExactAlarm(null)
        } else openSetting()
        // TODO: Check logic here
        if (!sharedPref.getBoolean(IS_STEP_COUNT_INEXACT_ALARM_SET, false)) {
//            stepCountInExactAlarms.scheduleInExactAlarm(
//                System.currentTimeMillis(), 60_000
//            )
//            Log.d("Step count in exact alarm set: ", "true")
//            with(sharedPref.edit()) {
//                putBoolean(IS_STEP_COUNT_INEXACT_ALARM_SET, true)
//                apply()
//            }
        }
    }

    private fun openSetting() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
        }
    }

    companion object {
        const val homeTag = "Home flow tag"
    }
}
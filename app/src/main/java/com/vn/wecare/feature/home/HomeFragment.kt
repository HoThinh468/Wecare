package com.vn.wecare.feature.home

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.core.alarm.ExactAlarms
import com.vn.wecare.core.alarm.InExactAlarms
import com.vn.wecare.databinding.FragmentHomeBinding
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.feature.home.step_count.alarm.IS_STEP_COUNT_INEXACT_ALARM_SET
import com.vn.wecare.feature.home.step_count.alarm.STEP_COUNT_ALARM
import com.vn.wecare.feature.home.step_count.ui.view.StepCountFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    @Inject
    lateinit var stepCountExactAlarms: ExactAlarms

    @Inject
    lateinit var stepCountInExactAlarms: InExactAlarms

    private val stepCountViewModel: StepCountViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        homeViewModel.apply {
            checkIfUserIsNull()
            checkIfAdditionalInformationMissing()
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.homeUIState.collect {
                    if (it.isUserNull) {
                        findNavController().navigate(R.id.action_homeFragment_to_authentication_nested_graph)
                        homeViewModel.resetUserNull()
                    }
                    if (it.isAdditionInfoMissing) {
                        findNavController().navigate(R.id.action_global_onboardingFragment)
                        homeViewModel.resetUserAdditionalInformationRes()
                    }
                }
            }
        }

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
                stepCountViewModel = stepCountViewModel
            )
        }
    }

    override fun setupWhatNeeded() {
        super.setupWhatNeeded()
        val sharedPref =
            requireActivity().getSharedPreferences(STEP_COUNT_ALARM, Context.MODE_PRIVATE)
        // Open dialog to request for schedule exact alarm
        if (stepCountExactAlarms.canScheduleExactAlarm()) {
//            stepCountExactAlarms.scheduleExactAlarm(null)
        } else {
            openSetting()
        }
        // TODO: Check logic here
        Log.d(
            StepCountFragment.stepCountTag, "I s step count inexact set: ${
                sharedPref.getBoolean(
                    IS_STEP_COUNT_INEXACT_ALARM_SET, false
                )
            }"
        )
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

    // Call this function in the main screen to get needed permissions
    private fun openSetting() {
        // If android is higher than android 12 than open the settings to allow permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
        }
    }
}
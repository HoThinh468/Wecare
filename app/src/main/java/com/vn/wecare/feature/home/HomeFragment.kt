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
import com.vn.wecare.feature.home.step_count.alarm.IS_STEP_COUNT_EXACT_ALARM_SET
import com.vn.wecare.feature.home.step_count.alarm.IS_STEP_COUNT_INEXACT_ALARM_SET
import com.vn.wecare.feature.home.step_count.alarm.STEP_COUNT_ALARM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    @Inject
    lateinit var stepCountExactAlarms: ExactAlarms

    @Inject
    lateinit var stepCountInExactAlarms: InExactAlarms

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
                stepCountViewModel = stepCountViewModel,
                cancelInExactAlarm = { stepCountInExactAlarms.clearInExactAlarm() },
            )
        }
    }

    override fun setupWhatNeeded() {
        super.setupWhatNeeded()
        val sharedPref = requireActivity().getSharedPreferences(STEP_COUNT_ALARM, Context.MODE_PRIVATE)
        // Open dialog to request for schedule exact alarm
        if (stepCountExactAlarms.canScheduleExactAlarm()) {
            if (sharedPref.getBoolean(IS_STEP_COUNT_EXACT_ALARM_SET, false)) {
//                stepCountExactAlarms.scheduleExactAlarm(null)
                with(sharedPref.edit()) {
                    putBoolean(IS_STEP_COUNT_EXACT_ALARM_SET, true)
                }
            }
        } else {
            openSetting()
        }
        if (sharedPref.getBoolean(IS_STEP_COUNT_INEXACT_ALARM_SET, false)) {
//        stepCountInExactAlarms.scheduleInExactAlarm(
//            getEndOfTheDayMilliseconds(), ONE_HOUR_INTERVAL_MILLIS
//        )
            with(sharedPref.edit()) {
                putBoolean(IS_STEP_COUNT_INEXACT_ALARM_SET, true)
            }
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
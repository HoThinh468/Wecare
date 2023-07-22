package com.vn.wecare.feature.home

import android.app.AlarmManager
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.BaseBindingFragment
import com.vn.wecare.core.WecareUserSingletonObject
import com.vn.wecare.core.alarm.ExactAlarms
import com.vn.wecare.core.alarm.InExactAlarms
import com.vn.wecare.databinding.FragmentHomeBinding
import com.vn.wecare.feature.home.step_count.ui.view.StepCountFragment
import com.vn.wecare.utils.getEndOfTheDayMilliseconds
import com.vn.wecare.utils.getTheEndOfCurrentHourMilliseconds
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

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun setupComposeView(composeView: ComposeView?, content: @Composable (() -> Unit)?) {
        Log.d(homeTag, "User singleton: ${WecareUserSingletonObject.getInstance()}")
        super.setupComposeView(
            binding.homeComposeView
        ) {
            homeViewModel.initHomeUIState()
            HomeScreen(
                onDashboardCardClick = {
                    findNavController().safeNavigate(
                        R.id.homeFragment, R.id.action_homeFragment_to_dashboard_graph
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
                homeViewModel = homeViewModel
            )
        }
    }

    override fun setupWhatNeeded() {
        super.setupWhatNeeded()
        setupStepCountExactAlarm()
        setUpStepCountInExactAlarm()
    }

    private fun setupStepCountExactAlarm() {
        // Setup exact alarm
        if (stepCountExactAlarms.canScheduleExactAlarm()) {
            if (stepCountExactAlarms.isExactAlarmSet()) {
                Log.d(StepCountFragment.stepCountTag, "Exact alarm set up already")
            } else {
                stepCountExactAlarms.scheduleExactAlarm(getEndOfTheDayMilliseconds())
            }
        } else openSetting()
    }

    private fun setUpStepCountInExactAlarm() {
        if (stepCountInExactAlarms.isInExactAlarmSet()) {
            Log.d(StepCountFragment.stepCountTag, "Repeating alarm setup already")
        } else {
            stepCountInExactAlarms.scheduleRepeatingInExactAlarm(
                getTheEndOfCurrentHourMilliseconds(), AlarmManager.INTERVAL_HOUR
            )
        }
    }

    private fun openSetting() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
        }
    }

    companion object {
        const val homeTag = "Home flow"
    }
}
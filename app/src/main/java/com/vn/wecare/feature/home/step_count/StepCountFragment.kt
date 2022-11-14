package com.vn.wecare.feature.home.step_count

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentStepCountBinding
import com.vn.wecare.feature.home.step_count.compose.StepCountScreen
import com.vn.wecare.ui.theme.WecareTheme

class StepCountFragment : Fragment() {

    private var _binding: FragmentStepCountBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStepCountBinding.inflate(inflater, container, false)

        binding.stepCountComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    StepCountScreen(
                        navigateUp = { findNavController().popBackStack() },
                        moveToSetGoalScreen = {
                            val action = R.id.action_stepCountFragment_to_setYourGoalsFragment
                            findNavController().navigate(action)
                        },
                    )
                }
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
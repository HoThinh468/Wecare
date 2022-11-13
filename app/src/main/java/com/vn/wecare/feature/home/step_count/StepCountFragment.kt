package com.vn.wecare.feature.home.step_count

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.Navigation
import com.vn.wecare.R
import com.vn.wecare.core_navigation.HomeRoutes
import com.vn.wecare.databinding.FragmentHomeBinding
import com.vn.wecare.databinding.FragmentStepCountBinding
import com.vn.wecare.feature.home.view.HomeScreen

class StepCountFragment : Fragment() {

    private var _binding: FragmentStepCountBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStepCountBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.stepCountComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                StepCountScreen(
                    navigateUp = { },
                    moveToSetGoalScreen = {}
                )
            }
        }
        return view
    }
}
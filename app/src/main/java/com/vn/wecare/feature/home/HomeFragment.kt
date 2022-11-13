package com.vn.wecare.feature.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.Navigation
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentHomeBinding
import com.vn.wecare.feature.home.view.HomeScreen

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.homeComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                HomeScreen(
                    onFootStepCountCardClick = { Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_stepCountFragment) },
                    onTrainingClick = {Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_trainingFragment)},
                    onWaterCardClick = {},
                    onBMICardClick = {},
                    onWalkingIcClick = {},
                    onRunningIcClick = {},
                    onBicycleIcClick = {},
                    onMeditationIcClick = {}
                )
            }
        }
        return view
    }
}
package com.vn.wecare.feature.training.dashboard

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentTrainingBinding
import com.vn.wecare.ui.theme.WecareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrainingFragment : Fragment() {
    private var _binding: FragmentTrainingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrainingBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.trainingComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    TrainingScreen(
                        moveToWalkingScreen = {
                            setFragmentResult("userAction", bundleOf("userAction" to it))
                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_trainingFragment_to_walkingFragment)
                        },
                        navigateBack = {
                            Navigation.findNavController(requireView()).popBackStack()
                        },
                        moveToRunningScreen = {
                            setFragmentResult("userAction", bundleOf("userAction" to it))
                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_trainingFragment_to_walkingFragment)
                        },
                        moveToCyclingScreen = {
                            setFragmentResult("userAction", bundleOf("userAction" to it))
                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_trainingFragment_to_walkingFragment)
                        },
                        moveToMeditationScreen = {
                            setFragmentResult("userAction", bundleOf("userAction" to it))
                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_trainingFragment_to_walkingFragment)
                        },
                    )
                }
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
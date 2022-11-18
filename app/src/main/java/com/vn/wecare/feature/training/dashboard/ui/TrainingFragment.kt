package com.vn.wecare.feature.training.dashboard.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.Navigation
import com.vn.wecare.R
import com.vn.wecare.core_navigation.TrainingRoutes
import com.vn.wecare.databinding.FragmentHomeBinding
import com.vn.wecare.databinding.FragmentTrainingBinding
import com.vn.wecare.feature.home.view.HomeScreen
import com.vn.wecare.ui.theme.WecareTheme

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
                        moveToWalkingScreen = { Navigation.findNavController(requireView()).navigate(R.id.action_trainingFragment_to_walkingFragment) }
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
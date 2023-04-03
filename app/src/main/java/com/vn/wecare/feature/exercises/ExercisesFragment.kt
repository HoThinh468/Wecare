package com.vn.wecare.feature.exercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.findNavController
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentExercisesBinding
import com.vn.wecare.databinding.FragmentTrainingBinding
import com.vn.wecare.ui.theme.WecareTheme

class ExercisesFragment : Fragment() {

    private var _binding: FragmentExercisesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExercisesBinding.inflate(inflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    ExercisesScreen(
                        userName = "trung",
                        onNavigateToReport = {},
                        onNavigateToEndurance = { findNavController().navigate(R.id.action_exercisesFragment_to_exerciseListFragment) }
                    )
                }
            }
        }

        return binding.root
    }
}
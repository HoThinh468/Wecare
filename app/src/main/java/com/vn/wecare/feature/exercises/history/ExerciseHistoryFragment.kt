package com.vn.wecare.feature.exercises.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.findNavController
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentExerciseHistoryBinding
import com.vn.wecare.databinding.FragmentProgramDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseHistoryFragment : Fragment() {

    private var _binding: FragmentExerciseHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseHistoryBinding.inflate(inflater, container, false)

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ExerciseHistoryScreen(
                    onNavigationBack = {
                        findNavController().popBackStack()
                    }
                )
            }
        }

        return binding.root
    }
}
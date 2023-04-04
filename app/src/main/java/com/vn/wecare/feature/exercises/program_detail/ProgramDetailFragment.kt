package com.vn.wecare.feature.exercises.program_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.model.ListExerciseItem
import com.vn.wecare.databinding.FragmentProgramDetailBinding
import com.vn.wecare.feature.exercises.ExercisesViewModel
import com.vn.wecare.ui.theme.WecareTheme

class ProgramDetailFragment : Fragment() {

    private var _binding: FragmentProgramDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    val viewModel: ExercisesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProgramDetailBinding.inflate(inflater, container, false)

        val uiState: ListExerciseItem = arguments?.getSerializable("listDetail") as ListExerciseItem

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    ProgramDetailScreen(
                        onNavigationBack = { findNavController().popBackStack() },
                        title = uiState.title,
                        level = uiState.level,
                        duration = uiState.duration,
                        description = uiState.description,
                        rating = 4,
                        ratedNumber = 231,
                        onNavigateToRatingScreen = {
                            findNavController().navigate(R.id.action_programDetailFragment_to_programRatingFragment)
                        },
                        onStartWorkout = {
                            viewModel.setCurrentWorkoutList(uiState.listExerciseDetail)
                            findNavController().navigate(R.id.action_programDetailFragment_to_workoutRestFragment2)
                        },
                        listExercises = uiState.listExerciseDetail
                    )
                }
            }
        }

        return binding.root
    }
}
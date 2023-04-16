package com.vn.wecare.feature.exercises.program_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.model.ListExerciseItem
import com.vn.wecare.databinding.FragmentProgramDetailBinding
import com.vn.wecare.feature.exercises.ExercisesViewModel
import com.vn.wecare.feature.exercises.program_ratings.ProgramRatingUI
import com.vn.wecare.ui.theme.WecareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProgramDetailFragment : Fragment() {

    private var _binding: FragmentProgramDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val exerciseViewModel: ExercisesViewModel by activityViewModels()
    val detailViewModel: ProgramDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProgramDetailBinding.inflate(inflater, container, false)

        val uiState: Pair<ListExerciseItem, Int> = arguments?.getSerializable("listDetail") as Pair<ListExerciseItem, Int>
        detailViewModel.getListReview(uiState.first.type, uiState.second)

        exerciseViewModel.setCurrentIndex(uiState.second)
        exerciseViewModel.setCurrentType(uiState.first.type)

        lateinit var bundle: Bundle

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    ProgramDetailScreen(
                        onNavigationBack = { findNavController().popBackStack() },
                        title = uiState.first.title,
                        level = uiState.first.level,
                        duration = uiState.first.duration,
                        description = uiState.first.description,
                        onNavigateToRatingScreen = {
                            bundle = bundleOf("programRatingUI" to detailViewModel.listReviews.value?.let {
                                ProgramRatingUI(
                                    title = uiState.first.title,
                                    listReview = it,
                                    ratedNumber = detailViewModel.getReviewCount(it),
                                    rating = detailViewModel.getRating(it),
                                    type = uiState.first.type,
                                    exerciseIndex = uiState.second
                                )
                            })
                            findNavController().navigate(R.id.action_programDetailFragment_to_programRatingFragment, bundle)
                        },
                        onStartWorkout = {
                            exerciseViewModel.onStartWorkout()
                            exerciseViewModel.setCurrentWorkoutList(uiState.first.listExerciseDetail)
                            findNavController().navigate(R.id.action_programDetailFragment_to_workoutRestFragment2)
                        },
                        listExercises = uiState.first.listExerciseDetail
                    )
                }
            }
        }

        return binding.root
    }
}
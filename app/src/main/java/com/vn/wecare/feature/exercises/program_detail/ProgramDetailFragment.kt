package com.vn.wecare.feature.exercises.program_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.findNavController
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentExerciseListBinding
import com.vn.wecare.databinding.FragmentProgramDetailBinding
import com.vn.wecare.feature.exercises.exercise_list.ExerciseLevel
import com.vn.wecare.feature.exercises.exercise_list.ExerciseListScreen
import com.vn.wecare.ui.theme.WecareTheme

class ProgramDetailFragment : Fragment() {

    private var _binding: FragmentProgramDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProgramDetailBinding.inflate(inflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    ProgramDetailScreen(
                        onNavigationBack = { findNavController().popBackStack() },
                        isLiked = true,
                        title = "High intensity full body workout",
                        level = ExerciseLevel.Hard,
                        duration = 20,
                        description = "High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
                        rating = 4,
                        ratedNumber = 231,
                        onNavigateToRatingScreen = {
                            findNavController().navigate(R.id.action_programDetailFragment_to_programRatingFragment)
                        }
                    )
                }
            }
        }

        return binding.root
    }
}
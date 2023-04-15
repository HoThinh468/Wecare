package com.vn.wecare.feature.exercises.program_ratings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.findNavController
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentExercisesBinding
import com.vn.wecare.databinding.FragmentProgramRatingBinding
import com.vn.wecare.feature.exercises.ExercisesScreen
import com.vn.wecare.ui.theme.WecareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProgramRatingFragment : Fragment() {

    private var _binding: FragmentProgramRatingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val uiState: ProgramRatingUI = arguments?.getSerializable("programRatingUI") as ProgramRatingUI
        val viewModel: ProgramRatingsViewModel by viewModels()
        viewModel.setListReview(uiState.listReview)

        _binding = FragmentProgramRatingBinding.inflate(inflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    ProgramRatingsScreen(
                        onNavigationBack = { findNavController().popBackStack() },
                        title = uiState.title,
                        rating = uiState.rating,
                        ratedNumber = uiState.ratedNumber,
                        exerciseIndex = uiState.exerciseIndex,
                        exerciseType = uiState.type
                    )
                }
            }
        }

        return binding.root
    }
}
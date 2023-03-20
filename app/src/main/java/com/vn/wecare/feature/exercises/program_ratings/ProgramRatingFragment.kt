package com.vn.wecare.feature.exercises.program_ratings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProgramRatingBinding.inflate(inflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    ProgramRatingsScreen(
                        onNavigationBack = { findNavController().popBackStack() },
                        title = "High intensity full body workout",
                        rating = 4,
                        ratedNumber = 231
                    )
                }
            }
        }

        return binding.root
    }
}
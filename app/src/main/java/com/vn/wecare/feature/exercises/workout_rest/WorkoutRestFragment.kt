package com.vn.wecare.feature.exercises.workout_rest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentWorkoutRestBinding
import com.vn.wecare.feature.exercises.ExercisesViewModel
import com.vn.wecare.ui.theme.WecareTheme

class WorkoutRestFragment : Fragment() {
    private var _binding: FragmentWorkoutRestBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    val viewModel: ExercisesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutRestBinding.inflate(inflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    WorkoutRestScreen(
                        title = viewModel.getCurrentWorkout().title,
                        duration = viewModel.getCurrentWorkout().rest,
                        exercise = viewModel.getCurrentWorkout().exercise,
                        onNavigateToWorkoutPage = {
                            findNavController().navigate(R.id.action_workoutRestFragment2_to_workoutPageFragment)
                        }
                    )
                }
            }
        }
        return binding.root
    }
}
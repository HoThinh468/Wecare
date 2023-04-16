package com.vn.wecare.feature.exercises.workout_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentExercisesBinding
import com.vn.wecare.databinding.FragmentWorkoutPageBinding
import com.vn.wecare.feature.exercises.ExercisesViewModel
import com.vn.wecare.ui.theme.WecareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutPageFragment : Fragment() {

    private var _binding: FragmentWorkoutPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    val viewModel: ExercisesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutPageBinding.inflate(inflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    WorkoutPageScreen(
                        onQuit = { /*TODO*/ },
                        title = viewModel.getCurrentWorkout().title,
                        duration = viewModel.getCurrentWorkout().duration,
                        exercise = viewModel.getCurrentWorkout().exercise,
                        onNavigateToRest = {
                            if (viewModel.currentWorkoutIndex.value == viewModel.currentWorkoutList.value.size - 1) {
                                viewModel.onEndWorkout()
                                findNavController().navigate(R.id.action_workoutPageFragment_to_doneFragment2)
                            } else {
                                viewModel.increaseWorkoutIndex()
                                findNavController().navigate(R.id.action_workoutPageFragment_to_workoutRestFragment2)
                            }
                        },
                        onNavigateToPreviousRest = {
                            viewModel.decreaseWorkoutIndex()
                            findNavController().navigate(R.id.action_workoutPageFragment_to_workoutRestFragment2)
                        },
                        viewModel = viewModel
                    )
                }
            }
        }
        return binding.root
    }
}

fun handleOnBackPress() {

}
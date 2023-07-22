package com.vn.wecare.feature.exercises.workout_dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.vn.wecare.R
import com.vn.wecare.core.model.ListExerciseItem
import com.vn.wecare.databinding.FragmentExerciseDashboardBinding
import com.vn.wecare.databinding.FragmentProgramDetailBinding
import com.vn.wecare.feature.exercises.ExercisesViewModel
import com.vn.wecare.feature.exercises.program_detail.ProgramDetailScreen
import com.vn.wecare.feature.exercises.program_detail.ProgramDetailViewModel
import com.vn.wecare.feature.exercises.program_ratings.ProgramRatingUI
import com.vn.wecare.feature.food.dashboard.viewmodel.NutritionDashboardUiState
import com.vn.wecare.feature.home.HomeUiState
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.ui.theme.WecareTheme
import com.vn.wecare.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseDashboardFragment : Fragment() {
    private var _binding: FragmentExerciseDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    val viewModel: ExerciseDashboardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseDashboardBinding.inflate(inflater, container, false)

        viewModel.getCaloPerDay()
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    val goal = LatestGoalSingletonObject.getInStance()

                    ExerciseDashboardScreen(
                        onFootStepCountCardClick = {
                            findNavController().safeNavigate(
                                R.id.exerciseDashboardFragment, R.id.action_exerciseDashboardFragment_to_step_count_nested_graph
                            )
                                                   },
                        uiState = viewModel.homeUiState.collectAsState().value,
                        onTrainingClick = {
                            findNavController().safeNavigate(
                                R.id.exerciseDashboardFragment, R.id.action_exerciseDashboardFragment_to_training_graph
                            )
                        },
                        onWalkingIcClick = {},
                        onRunningIcClick = {},
                        onBicycleIcClick = {},
                        onMeditationIcClick = {},
                        onNavigateToExercise = {
                            findNavController().safeNavigate(
                                R.id.exerciseDashboardFragment, R.id.action_exerciseDashboardFragment_to_exercises_graph
                            )
                        },
                        reportState = NutritionDashboardUiState().copy(
                            targetCaloriesAmount = goal.caloriesBurnedEachDayGoal,
                            targetProteinIndex = goal.caloriesBurnedEachDayGoal / 3,
                            targetFatIndex = 0,
                            targetCarbsIndex = goal.caloriesBurnedEachDayGoal / 3 * 2
                        ),
                    )
                }
            }
        }

        return binding.root
    }
}
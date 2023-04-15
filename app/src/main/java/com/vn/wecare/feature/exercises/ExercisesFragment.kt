package com.vn.wecare.feature.exercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.vn.wecare.R
import com.vn.wecare.databinding.FragmentExercisesBinding
import com.vn.wecare.databinding.FragmentTrainingBinding
import com.vn.wecare.feature.exercises.exercise_list.ExerciseListViewModel
import com.vn.wecare.ui.theme.WecareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExercisesFragment : Fragment() {

    private var _binding: FragmentExercisesBinding? = null
    private val binding get() = _binding!!

    val viewModel: ExercisesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExercisesBinding.inflate(inflater, container, false)

        lateinit var bundle: Bundle

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WecareTheme {
                    ExercisesScreen(
                        userName = "trung",
                        onNavigateToReport = {},
                        onNavigateToEndurance = {
                            bundle = bundleOf("exerciseList" to viewModel.enduranceList)
                            findNavController().navigate(R.id.action_exercisesFragment_to_exerciseListFragment, bundle)
                        },
                        onNavigateToStrength = {
                            bundle = bundleOf("exerciseList" to viewModel.strengthList)
                            findNavController().navigate(
                                R.id.action_exercisesFragment_to_exerciseListFragment,
                                bundle
                            )
                        },
                        onNavigateToBalance = {
                            bundle = bundleOf("exerciseList" to viewModel.balanceList)
                            findNavController().navigate(
                                R.id.action_exercisesFragment_to_exerciseListFragment,
                                bundle
                            )
                        },
                        onNavigateToFlexibility = {
                            bundle = bundleOf("exerciseList" to viewModel.flexibilityList)
                            findNavController().navigate(
                                R.id.action_exercisesFragment_to_exerciseListFragment,
                                bundle
                            )
                        },
                        onNavigateToFullBody = {
                            findNavController().navigate(
                                R.id.action_exercisesFragment_to_fullBodyFragment
                            )
                        }
                    )
                }
            }
        }

        return binding.root
    }
}